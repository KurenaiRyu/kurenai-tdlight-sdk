package moe.kurenai.tdlight.client

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.login.AuthorizationStateType
import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.request.MediaRequest
import moe.kurenai.tdlight.request.Ping
import moe.kurenai.tdlight.request.Request
import moe.kurenai.tdlight.request.login.AuthCode
import moe.kurenai.tdlight.request.login.AuthPassword
import moe.kurenai.tdlight.request.login.UserLogin
import moe.kurenai.tdlight.request.message.*
import moe.kurenai.tdlight.util.DefaultMapper.MAPPER
import moe.kurenai.tdlight.util.DefaultMapper.convertToByteArray
import moe.kurenai.tdlight.util.DefaultMapper.parse
import moe.kurenai.tdlight.util.MultiPartBodyPublisher
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.function.Function

class TDLightClient(
    private val baseUrl: String,
    var token: String? = null,
    isUserMode: Boolean = true,
    private val clientPool: ExecutorService = defaultClientPool(),
    private val handlerPool: ExecutorService = defaultHandlerPool(),
    private val isDebugEnabled: Boolean = true
) {

    companion object {
        private val log = LogManager.getLogger()

        private const val MAX_RETRY = 3

        private fun defaultClientPool(): ThreadPoolExecutor {
            val number = AtomicInteger(0)
            return ThreadPoolExecutor(
                5, 10, 30L, TimeUnit.SECONDS,
                LinkedBlockingQueue(300)
            ) { r ->
                val t = Thread(
                    Thread.currentThread().threadGroup, r,
                    "telegram-client-${number.getAndIncrement()}",
                    0
                )
                if (t.isDaemon) t.isDaemon = false
                if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                t
            }
        }

        private fun defaultHandlerPool(): ThreadPoolExecutor {
            val number = AtomicInteger(0)
            return ThreadPoolExecutor(
                5, 10, 30L, TimeUnit.SECONDS,
                LinkedBlockingQueue(300)
            ) { r ->
                val t = Thread(
                    Thread.currentThread().threadGroup, r,
                    "telegram-handler-${number.getAndIncrement()}",
                    0
                )
                if (t.isDaemon) t.isDaemon = false
                if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                t
            }
        }
    }

    val tokenFile = File("./config/token.txt")

    private val DEFAULT_TIMEOUT = Duration.ofSeconds(30)
    private val DEFAULT_MIME_TYPE = "application/json"
    private val mode: String = if (isUserMode) "user" else "bot"

    init {
        if (token == null) {
            if (tokenFile.exists()) {
                token = tokenFile.readText()
            } else {
                if (isUserMode) handleLogin()
                else throw RuntimeException("Not found token.")
            }
        }
        val ping = sendSync(Ping())
        log.info("Ping $ping")
    }

    fun <T> send(request: Request<ResponseWrapper<T>>, timeout: Duration? = null): CompletableFuture<T> {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        val client = HttpClient.newBuilder().executor(clientPool).build()
        val httpRequest = buildRequest(uri, request, timeout)
        val bodyHandler: HttpResponse.BodyHandler<ByteArray> = HttpResponse.BodyHandlers.ofByteArray()
        return client
            .sendAsync(httpRequest, bodyHandler)
            .handleAsync({ resp, t -> tryResend(client, httpRequest, bodyHandler, resp, t) }, handlerPool)
            .thenCompose(Function.identity())
            .thenApplyAsync({ response: HttpResponse<ByteArray> -> response.log() }, handlerPool)
            .thenApplyAsync({ response: HttpResponse<ByteArray> -> response.parse(request.responseType) }, handlerPool)
    }

    fun <T> sendSync(request: Request<ResponseWrapper<T>>, timeout: Duration? = null): T {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        val client = HttpClient.newBuilder().executor(clientPool).build()
        val httpRequest = buildRequest(uri, request, timeout)
        val handler = HttpResponse.BodyHandlers.ofByteArray()

        return tryResendSync(client, httpRequest, handler)
            .log()
            .parse(request.responseType)
    }

    private fun shouldRetry(throwable: Throwable?, count: Int): Boolean {
        return if (count >= MAX_RETRY) false
        else throwable != null
    }

    private fun <T> tryResend(
        client: HttpClient,
        request: HttpRequest,
        handler: HttpResponse.BodyHandler<T>,
        resp: HttpResponse<T>?,
        throwable: Throwable?,
        count: Int = 1
    ): CompletableFuture<HttpResponse<T>> {
        return if (shouldRetry(throwable, count)) {
            client.sendAsync(request, handler)
                .handleAsync({ r, t -> tryResend(client, request, handler, r, t, count + 1) }, handlerPool)
                .thenCompose(Function.identity())
        } else if (throwable != null) {
            CompletableFuture.failedFuture(throwable)
        } else {
            CompletableFuture.completedFuture(resp)
        }
    }

    private fun <T> tryResendSync(client: HttpClient, request: HttpRequest, handler: HttpResponse.BodyHandler<T>, count: Int = 1): HttpResponse<T> {
        return try {
            client.send(request, handler)
        } catch (e: Exception) {
            if (shouldRetry(e, count)) {
                tryResendSync(client, request, handler, count + 1)
            } else {
                throw e
            }
        }
    }

    private fun <T> buildRequest(uri: URI, request: Request<T>, timeout: Duration? = null): HttpRequest {
        val httpRequest = HttpRequest.newBuilder()
        if (request.httpMethod == HttpMethod.POST) {
            if (request is MediaRequest) {
                multiPartBodyPublisher(MAPPER.valueToTree(request))?.let { multiPartBodyPublisher ->
                    httpRequest
                        .header("Content-Type", "multipart/form-data; charset=utf-8; boundary=${multiPartBodyPublisher.boundary}")
                        .POST(multiPartBodyPublisher.build())
                    if (isDebugEnabled) printDebugMultipartData(multiPartBodyPublisher.getByteArras())
                } ?: kotlin.run {
                    httpRequest
                        .header("Content-Type", DEFAULT_MIME_TYPE)
                        .headers("Accept", DEFAULT_MIME_TYPE)
                }
            } else {
                httpRequest
                    .header("Content-Type", DEFAULT_MIME_TYPE)
                    .headers("Accept", DEFAULT_MIME_TYPE)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(convertToByteArray(request).also { printDebugMultipartData(listOf(it)) }))
            }
        }
        if (timeout != Duration.ZERO) httpRequest.timeout(timeout ?: DEFAULT_TIMEOUT)
        return httpRequest.uri(uri).build()
    }

    private fun multiPartBodyPublisher(data: JsonNode): MultiPartBodyPublisher? {
        if (data.isEmpty) return null

        val multiPartBodyPublisher = MultiPartBodyPublisher()
        for (field in data.fields()) {
            field.value?.let { node ->
                if (node.has("attach_name")) {
                    val file = MAPPER.treeToValue(node, InputFile::class.java)
                    handleInputFile(field.key, file, multiPartBodyPublisher)
                } else if (field.key == "media" && node.isArray) {
                    val medias = ArrayList<HashMap<String, Any>>()
                    when (node[0]["type"].textValue()) {
                        InputMediaType.PHOTO -> {
                            val files = MAPPER.convertValue(node, object : TypeReference<List<InputMediaPhoto>>() {})
                            for (file in files) {
                                val map = MAPPER.convertValue(file, object : TypeReference<HashMap<String, Any>>() {})
                                file.media.handleMedia(map, multiPartBodyPublisher)
                                medias.add(map)
                            }
                        }
                        InputMediaType.VIDEO -> {
                            val files = MAPPER.convertValue(node, object : TypeReference<List<InputMediaVideo>>() {})
                            for (file in files) {
                                val map = MAPPER.convertValue(file, object : TypeReference<HashMap<String, Any>>() {})
                                file.media.handleMedia(map, multiPartBodyPublisher)
                                file.thumb.handleThumb(map, multiPartBodyPublisher)
                                medias.add(map)
                            }
                        }
                        InputMediaType.ANIMATION -> {
                            val files = MAPPER.convertValue(node, object : TypeReference<List<InputMediaAnimation>>() {})
                            for (file in files) {
                                val map = MAPPER.convertValue(file, object : TypeReference<HashMap<String, Any>>() {})
                                file.media.handleMedia(map, multiPartBodyPublisher)
                                file.thumb.handleThumb(map, multiPartBodyPublisher)
                                medias.add(map)
                            }
                        }
                        InputMediaType.AUDIO -> {
                            val files = MAPPER.convertValue(node, object : TypeReference<List<InputMediaAudio>>() {})
                            for (file in files) {
                                val map = MAPPER.convertValue(file, object : TypeReference<HashMap<String, Any>>() {})
                                file.media.handleMedia(map, multiPartBodyPublisher)
                                file.thumb.handleThumb(map, multiPartBodyPublisher)
                                medias.add(map)
                            }
                        }
                        InputMediaType.DOCUMENT -> {
                            val files = MAPPER.convertValue(node, object : TypeReference<List<InputMediaDocument>>() {})
                            for (file in files) {
                                val map = MAPPER.convertValue(file, object : TypeReference<HashMap<String, Any>>() {})
                                file.media.handleMedia(map, multiPartBodyPublisher)
                                file.thumb.handleThumb(map, multiPartBodyPublisher)
                                medias.add(map)
                            }
                        }
                        else -> {}
                    }
                    multiPartBodyPublisher.addPart(field.key, MAPPER.writeValueAsString(medias))
                } else {
                    multiPartBodyPublisher.addPart(field.key, field.value.textValue())
                }
            }
        }
        return multiPartBodyPublisher
    }

    private fun handleInputFile(key: String, inputFile: InputFile, publisher: MultiPartBodyPublisher) {
        if (inputFile.file != null) {
            publisher.addFile(key, inputFile.file!!.toPath())
        } else if (inputFile.inputStream != null) {
            publisher.addFile(key, { inputFile.inputStream!! }, inputFile.fileName, inputFile.mimeType)
        } else {
            publisher.addPart(key, inputFile.attachName)
        }
    }

    private fun determineUri(request: Request<*>): URI {
        return URI.create(if (request.needToken) "$baseUrl/$mode$token/${request.method}" else "$baseUrl/${request.method}")
    }

    private fun printDebugMultipartData(byteArrays: Iterable<ByteArray>) {
        if (!isDebugEnabled) return
        val all = ByteArray(byteArrays.sumOf { a: ByteArray -> a.size })
        var pos = 0
        for (bin in byteArrays) {
            val length = bin.size
            System.arraycopy(bin, 0, all, pos, length)
            pos += length
        }
        val data = String(all)
        log.debug("Multipart request data \n{}", data)
    }

    private fun logResponse(response: HttpResponse<ByteArray>): HttpResponse<ByteArray> {
        if (isDebugEnabled) response.body()?.let { log.debug("Response ${String(it)}") }
        return response
    }

    private fun HttpResponse<ByteArray>.log(): HttpResponse<ByteArray> {
        return logResponse(this)
    }

    private fun handleLogin() {
        println("phone:")
        val phone = readLine()!!
        val res = sendSync(UserLogin(phone))
        log.info("State: {}", res)
        token = "user${res.token}"
        log.info("Token $token")
        tokenFile.writeText(token!!)
        var state = res.authorizationState
        while (state != AuthorizationStateType.READY) {
            state = when (state) {
                AuthorizationStateType.WAIT_CODE -> {
                    println("code:")
                    val code = readLine()!!.toInt()
                    sendSync(AuthCode(code))
                }
                AuthorizationStateType.WAIT_PASSWORD -> {
                    println("pwd:")
                    val pwd = readLine()!!
                    sendSync(AuthPassword(pwd))
                }
                else -> {
                    throw Exception("login error")
                }
            }.also { log.info("State: $it") }.authorizationState
        }
        tokenFile.writeText(token!!)
    }

    private fun InputFile.handleMedia(map: HashMap<String, Any>, multiPartBodyPublisher: MultiPartBodyPublisher) {
        this.fileName?.also {
            map["fileName"] = it
            handleInputFile(it, this, multiPartBodyPublisher)
        } ?: this.attachName
        map["media"] = this.attachName
    }

    private fun InputFile?.handleThumb(map: HashMap<String, Any>, multiPartBodyPublisher: MultiPartBodyPublisher) {
        this?.fileName?.also {
            handleInputFile(it, this, multiPartBodyPublisher)
        } ?: this?.attachName
        this?.attachName?.let { map["thumb"] = it }
    }

}