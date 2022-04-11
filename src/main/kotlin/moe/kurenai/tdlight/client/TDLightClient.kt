package moe.kurenai.tdlight.client

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.login.AuthorizationStateType
import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.User
import moe.kurenai.tdlight.request.GetMe
import moe.kurenai.tdlight.request.MediaRequest
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
    baseUrl: String = DEFAULT_BASE_URL,
    var token: String? = null,
    isUserMode: Boolean = true,
    private val clientPool: ExecutorService = defaultClientPool(),
    private val handlerPool: ExecutorService = defaultHandlerPool(),
    private val isDebugEnabled: Boolean = true,
    updateBaseUrl: String = baseUrl
) {

    companion object {
        private val log = LogManager.getLogger()

        private const val MAX_RETRY = 3
        private const val DEFAULT_BASE_URL = "https://api.telegram.org"

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
    var me: User
    val baseUrl: String
    val updateBaseUrl: String

    private val DEFAULT_TIMEOUT = Duration.ofSeconds(30)
    private val DEFAULT_MIME_TYPE = "application/json"
    private val mode: String = if (isUserMode) "user" else "bot"

    init {
        this.baseUrl = resolveBaseUrl(baseUrl)
        this.updateBaseUrl = resolveBaseUrl(updateBaseUrl)

        if (token == null) {
            if (tokenFile.exists()) {
                token = tokenFile.readText()
            } else {
                if (isUserMode) handleLogin()
                else throw RuntimeException("Not found token.")
            }
        }
        me = sendSync(GetMe())

        log.info("Login by ${me.firstName}${me.lastName?.let { " $it" } ?: ""} @${me.username}")
    }

    private fun resolveBaseUrl(url: String): String {
        return if (url == DEFAULT_BASE_URL) {
            DEFAULT_BASE_URL
        } else {
            val uri = URI(url)
            if (uri.host == "api.telegram.org") DEFAULT_BASE_URL
            else if (uri.path == "/bot") url.replace("/bot", "")
            else url
        }
    }


    fun <T> send(
        request: Request<ResponseWrapper<T>>,
        timeout: Duration? = null,
        baseUrl: String? = null
    ): CompletableFuture<T> {
        val uri = determineUri(request, baseUrl)
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

    fun <T> sendSync(request: Request<ResponseWrapper<T>>, timeout: Duration? = null, baseUrl: String? = null): T {
        val uri = determineUri(request, baseUrl)
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
            log.error(throwable.message, throwable)
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
                multiPartBodyPublisher(request)?.let { multiPartBodyPublisher ->
                    httpRequest
                        .header(
                            "Content-Type",
                            "multipart/form-data; charset=utf-8; boundary=${multiPartBodyPublisher.boundary}"
                        )
                        .POST(multiPartBodyPublisher.build())
//                    if (isDebugEnabled) printDebugMultipartData(multiPartBodyPublisher.getByteArras())
                } ?: kotlin.run {
                    httpRequest
                        .header("Content-Type", DEFAULT_MIME_TYPE)
                        .headers("Accept", DEFAULT_MIME_TYPE)
                }
            } else {
                httpRequest
                    .header("Content-Type", DEFAULT_MIME_TYPE)
                    .headers(
                        "Accept", DEFAULT_MIME_TYPE,
                        "Keep-Alive", "timeout=${(timeout ?: DEFAULT_TIMEOUT).toSeconds() - 5}, max=1000"
                    )
                    .POST(HttpRequest.BodyPublishers.ofByteArray(convertToByteArray(request).also {
                        printDebugMultipartData(
                            listOf(it)
                        )
                    }))
            }
        }
        if (timeout != Duration.ZERO) httpRequest.timeout(timeout ?: DEFAULT_TIMEOUT)
        return httpRequest.uri(uri).build()
    }

    private fun multiPartBodyPublisher(data: MediaRequest): MultiPartBodyPublisher? {
        val fields = MAPPER.valueToTree<JsonNode>(data).fields()
        if (!fields.hasNext()) return null

        val multiPartBodyPublisher = MultiPartBodyPublisher()

        for (field in fields) {
            multiPartBodyPublisher.addPart(field.key, field.value.textValue())
        }

        when (data) {
            is SendAnimation -> {
                handleInputFile("animation", data.animation, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.animation.fileName)
                data.thumb?.let { handleInputFile("thumb", it, multiPartBodyPublisher) }
            }
            is SendPhoto -> {
                handleInputFile("photo", data.photo, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.photo.fileName)
            }
            is SendAudio -> {
                handleInputFile("audio", data.audio, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.audio.fileName)
            }
            is SendDocument -> {
                handleInputFile("document", data.document, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.document.fileName)
                data.thumb?.let { handleInputFile("thumb", it, multiPartBodyPublisher) }
            }
            is SendVideo -> {
                handleInputFile("video", data.video, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.video.fileName)
                data.thumb?.let { handleInputFile("thumb", it, multiPartBodyPublisher) }
            }
            is SendVoice -> {
                handleInputFile("voice", data.voice, multiPartBodyPublisher)
                multiPartBodyPublisher.addPart("filename", data.voice.fileName)
            }
            is SendMediaGroup -> {
                val list = ArrayList<HashMap<String, Any>>()
                for (inputMedia in data.media!!) {
                    val map = MAPPER.convertValue(inputMedia, jacksonTypeRef<HashMap<String, Any>>())
                    map["type"] = inputMedia.type
                    inputMedia.media.handleMedia(map, multiPartBodyPublisher)
                    list.add(map)
                }
                multiPartBodyPublisher.addPart("media", MAPPER.writeValueAsString(list))
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

    private fun determineUri(request: Request<*>, baseUrl: String? = null): URI {
        val url = baseUrl?.takeIf { it.isNotBlank() } ?: this.baseUrl
        return URI.create(if (request.needToken) "$url/$mode$token/${request.method}" else "$url/${request.method}")
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