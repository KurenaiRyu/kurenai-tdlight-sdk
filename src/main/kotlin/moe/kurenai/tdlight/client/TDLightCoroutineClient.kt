package moe.kurenai.tdlight.client

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonTypeRef
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.utils.io.streams.*
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
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
import moe.kurenai.tdlight.util.DefaultMapper
import moe.kurenai.tdlight.util.DefaultMapper.MAPPER
import moe.kurenai.tdlight.util.DefaultMapper.convertToByteArray
import moe.kurenai.tdlight.util.getLogger
import java.io.Closeable
import java.io.File
import java.net.URI
import java.time.Duration
import kotlin.collections.set
import kotlin.coroutines.CoroutineContext

class TDLightCoroutineClient(
    baseUrl: String = DEFAULT_BASE_URL,
    var token: String? = null,
    isUserMode: Boolean = false,
    private val isDebugEnabled: Boolean = true,
    updateBaseUrl: String = baseUrl
) : CoroutineScope, Closeable {

    companion object {
        private val log = getLogger()

        private const val MAX_RETRY = 3
        private const val DEFAULT_BASE_URL = "https://api.telegram.org"
    }

    override var coroutineContext: CoroutineContext = CoroutineName("TDLightCoroutineClient")

    val client = HttpClient(OkHttp) {
        install(HttpRequestRetry) {
            maxRetries = MAX_RETRY
            retryOnExceptionOrServerErrors(maxRetries)
            exponentialDelay()
        }
        install(HttpTimeout)
    }
    val tokenFile = File("./config/token.txt")
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
                throw RuntimeException("Not found token.")
            }
        }
    }

    suspend fun getMe(): User {
        return send(GetMe())
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

    suspend fun <T> send(
        request: Request<ResponseWrapper<T>>,
        timeout: Duration? = null,
        baseUrl: String? = null
    ): T {
        val uri = determineUri(request, baseUrl)
        if (isDebugEnabled) log.debug("Request to $uri")
        val response = client.request(uri.toURL()) { buildRequest(request, timeout) }
        val body = response.body<ByteArray>()
        if (isDebugEnabled) log.debug("Response ${String(body)}")
        return DefaultMapper.parseResponse(body, request.responseType)
    }

    private fun <T> HttpRequestBuilder.buildRequest(request: Request<T>, timeout: Duration? = null) {
        if (request.httpMethod == HttpMethod.POST) {
            method = io.ktor.http.HttpMethod.Post
            if (request is MediaRequest) {
                getFormData(request)?.let {
                    setBody(MultiPartFormDataContent(it))
                } ?: kotlin.run {
                    headers {
                        append(HttpHeaders.ContentType, DEFAULT_MIME_TYPE)
                        append(HttpHeaders.Accept, DEFAULT_MIME_TYPE)
                    }
                }
            } else {
                headers {
                    append(HttpHeaders.ContentType, DEFAULT_MIME_TYPE)
                    append(HttpHeaders.Accept, DEFAULT_MIME_TYPE)
                    append("Keep-Alive", "timeout=${(timeout ?: DEFAULT_TIMEOUT).toSeconds() - 5}, max=1000")
                }
                setBody(convertToByteArray(request).also {
                    printDebugMultipartData(
                        listOf(it)
                    )
                })
            }
        } else {
            method = io.ktor.http.HttpMethod.Get
        }
        if (timeout != Duration.ZERO) timeout {
            val value = (timeout ?: DEFAULT_TIMEOUT).toMillis()
            connectTimeoutMillis = value
            socketTimeoutMillis = value
            requestTimeoutMillis = value
        }
    }

    private fun getFormData(request: MediaRequest): List<PartData>? {
        val fields = MAPPER.valueToTree<JsonNode>(request).fields()
        return if (!fields.hasNext()) null
        else formData {
            for (field in fields) {
                field.value.textValue()?.let {
                    append(field.key, it)
                }
            }
            when (request) {
                is SendAnimation -> {
                    handleInputFile("animation", request.animation)
                    request.animation.fileName?.let {
                        append("filename", it)
                    }
                    request.thumb?.let { handleInputFile("thumb", it) }
                }

                is SendPhoto -> {
                    handleInputFile("photo", request.photo)
                    request.photo.fileName?.let {
                        append("filename", it)
                    }
                }

                is SendAudio -> {
                    handleInputFile("audio", request.audio)
                    request.audio.fileName?.let {
                        append("filename", it)
                    }
                }

                is SendDocument -> {
                    handleInputFile("document", request.document)
                    request.document.fileName?.let {
                        append("filename", it)
                    }
                    request.thumb?.let { handleInputFile("thumb", it) }
                }

                is SendVideo -> {
                    handleInputFile("video", request.video)
                    request.video.fileName?.let {
                        append("filename", it)
                    }
                    request.thumb?.let { handleInputFile("thumb", it) }
                }

                is SendVoice -> {
                    handleInputFile("voice", request.voice)
                    request.voice.fileName?.let {
                        append("filename", it)
                    }
                }

                is SendMediaGroup -> {
                    val list = ArrayList<HashMap<String, Any>>()
                    for (inputMedia in request.media!!) {
                        val map = MAPPER.convertValue(inputMedia, jacksonTypeRef<HashMap<String, Any>>())
                        map["type"] = inputMedia.type
                        val media = inputMedia.media
                        val fileName = media.fileName?.also {
                            map["fileName"] = it
                            handleInputFile(it, media)
                        } ?: media.attachName
                        map["media"] = fileName
                        list.add(map)
                    }
                    append("media", MAPPER.writeValueAsString(list)!!)
                }
            }
        }
    }

    private fun FormBuilder.handleInputFile(key: String, inputFile: InputFile) {
        if (inputFile.file != null) {
            val file = inputFile.file!!
            appendInput(key, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                inputFile.mimeType?.let {
                    append(HttpHeaders.ContentType, it)
                }
            }) {
                file.inputStream().buffered().asInput()
            }
        } else if (inputFile.inputStream != null) {
            appendInput(key, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=${inputFile.fileName}")
                inputFile.mimeType?.let {
                    append(HttpHeaders.ContentType, it)
                }
            }) {
                inputFile.inputStream!!.asInput()
            }
        } else {
            append(key, inputFile.attachName)
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

    private suspend fun handleLogin() {
        println("phone:")
        val phone = readLine()!!
        val res = send(UserLogin(phone))
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
                    send(AuthCode(code))
                }

                AuthorizationStateType.WAIT_PASSWORD -> {
                    println("pwd:")
                    val pwd = readLine()!!
                    send(AuthPassword(pwd))
                }

                else -> {
                    throw Exception("login error")
                }
            }.also { log.info("State: $it") }.authorizationState
        }
        tokenFile.writeText(token!!)
    }

    override fun close() {
        client.close()
    }

}