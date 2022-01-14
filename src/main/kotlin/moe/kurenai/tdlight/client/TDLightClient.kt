package moe.kurenai.tdlight.client

import moe.kurenai.tdlight.model.login.AuthorizationStateType
import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.request.MediaRequest
import moe.kurenai.tdlight.request.Ping
import moe.kurenai.tdlight.request.Request
import moe.kurenai.tdlight.request.login.AuthCode
import moe.kurenai.tdlight.request.login.AuthPassword
import moe.kurenai.tdlight.request.login.UserLogin
import moe.kurenai.tdlight.util.DefaultMapper.MAPPER
import moe.kurenai.tdlight.util.DefaultMapper.convertToByteArray
import moe.kurenai.tdlight.util.DefaultMapper.convertToMap
import moe.kurenai.tdlight.util.DefaultMapper.parse
import moe.kurenai.tdlight.util.MultiPartBodyPublisher
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpResponse
import java.nio.file.Files
import java.time.Duration
import java.util.concurrent.CompletableFuture

class TDLightClient(
    private val baseUrl: String,
    private var token: String? = null,
    isUserMode: Boolean = true,
    private val isDebugEnabled: Boolean = true
) {

    companion object {
        private val log = LogManager.getLogger()
    }

    val tokenFile = File("./config/token.txt")

    private val DEFAULT_TIMEOUT = Duration.ofSeconds(30)
    private val DEFAULT_MIME_TYPE = "application/json"
    private val mode: String = if (isUserMode) "user" else "bot"

    init {
        if (tokenFile.exists()) {
            token = tokenFile.readText()
        } else {
            handleLogin()
        }
        val ping = sendSync(Ping())
        log.info("Ping $ping")
        if (!ping.ok) throw Exception("Login failed.")
    }

    fun <T> send(request: Request<T>, timeout: Duration? = null): CompletableFuture<T> {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        return HttpClient.newHttpClient()
            .sendAsync(buildRequest(uri, request, timeout), HttpResponse.BodyHandlers.ofByteArray())
            .thenApplyAsync { response: HttpResponse<ByteArray> -> response.log() }
            .thenApply { response: HttpResponse<ByteArray> -> response.parse(request.responseType) }
    }

    fun <T> sendSync(request: Request<T>, timeout: Duration? = null): T {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        return HttpClient.newHttpClient()
            .send(buildRequest(uri, request, timeout), HttpResponse.BodyHandlers.ofByteArray())
            .log()
            .parse(request.responseType)
    }

    private fun <T> buildRequest(uri: URI, request: Request<T>, timeout: Duration? = null): HttpRequest? {
        val httpRequest = HttpRequest.newBuilder()
        if (request.httpMethod == HttpMethod.POST) {
            if (request is MediaRequest) {
                multiPartBodyPublisher(convertToMap(request))?.let { multiPartBodyPublisher ->
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

    private fun multiPartBodyPublisher(data: Map<Any, Any?>): MultiPartBodyPublisher? {
        if (data.isEmpty()) return null

        val multiPartBodyPublisher = MultiPartBodyPublisher()
        for (entry in data.entries) {
            entry.value?.let { value ->
                if (value is LinkedHashMap<*, *> && value.containsKey("attach_name")) {
                    val file = MAPPER.convertValue(value, InputFile::class.java)
                    if (file.file != null) {
                        multiPartBodyPublisher.addFile(entry.key.toString(), file.file!!.toPath())
                    } else if (file.inputStream != null) {
                        multiPartBodyPublisher.addFile(entry.key.toString(), { file.inputStream!! }, file.fileName, file.mimeType)
                    } else {
                        multiPartBodyPublisher.addPart(entry.key.toString(), file.attachName)
                    }
                } else {
                    multiPartBodyPublisher.addPart(entry.key.toString(), entry.value.toString())
                }
            }
        }
        return multiPartBodyPublisher
    }

    fun prepareMultipartData(data: Map<Any, Any?>, boundary: String): BodyPublisher? {
        val byteArrays = ArrayList<ByteArray>()
        val separator = "--$boundary\r\nContent-Disposition: form-data; name=".encodeToByteArray()
        for (entry in data.entries) {
            entry.value?.let { value ->
                byteArrays.add(separator)
                if (value is LinkedHashMap<*, *> && value.containsKey("attach_name")) {
                    val file = MAPPER.convertValue(value, InputFile::class.java)
                    byteArrays.add("\"${entry.key}\"; filename=\"${file.fileName}\"\r\nContent-Type: ${file.mimeType}\r\n\r\n".encodeToByteArray())
                    byteArrays.add(Files.readAllBytes(file.file!!.toPath()))
                    byteArrays.add("\r\n".encodeToByteArray())
                } else {
                    byteArrays.add("\"${entry.key}\"\r\n\r\n${entry.value}\r\n".encodeToByteArray())
                }
            }
        }
        byteArrays.add("--$boundary--".encodeToByteArray())
        if (isDebugEnabled) {
            printDebugMultipartData(byteArrays)
        }
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays)
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
        log.info("State: {}", res.result)
        token = "user${res.result!!.token}"
        log.info("Token $token")
        tokenFile.writeText(token!!)
        var state = res.result.authorizationState
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
            }.also { log.info("State: $it") }.result!!.authorizationState
        }
        tokenFile.writeText(token!!)
    }

}