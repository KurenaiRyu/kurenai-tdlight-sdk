package moe.kurenai.tdlight.client

import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.model.login.AuthorizationStateType
import moe.kurenai.tdlight.request.Ping
import moe.kurenai.tdlight.request.Request
import moe.kurenai.tdlight.request.login.AuthCode
import moe.kurenai.tdlight.request.login.AuthPassword
import moe.kurenai.tdlight.request.login.UserLogin
import moe.kurenai.tdlight.util.DefaultMapper.convertToByteArray
import moe.kurenai.tdlight.util.DefaultMapper.parse
import org.apache.logging.log4j.LogManager
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.CompletableFuture

class TDLightClient(
    val host: String,
    var token: String? = null,
    private val isUserMode: Boolean = true,
    private val isDebugEnabled: Boolean = true
){

    companion object {
        private val log = LogManager.getLogger()
    }

    val tokenFile = File("./config/token.txt")

    private val DEFAULT_TIMEOUT = Duration.ofSeconds(10)
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

    fun <T> send(request: Request<T>): CompletableFuture<T> {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        return when (request.httpMethod) {
            HttpMethod.GET -> {
                get(uri, request.responseType)
            }
            HttpMethod.POST -> {
                post(uri, request)
            }
        }
    }

    fun <T> sendSync(request: Request<T>): T {
        val uri = determineUri(request)
        if (isDebugEnabled) log.debug("Request to $uri")

        return when (request.httpMethod) {
            HttpMethod.GET -> {
                getSync(uri, request.responseType)
            }
            HttpMethod.POST -> {
                postSync(uri, request)
            }
        }
    }

    private fun <T> get(uri: URI, responseType: TypeReference<T>): CompletableFuture<T> {
        return HttpClient.newHttpClient()
            .sendAsync(buildRequest(uri), HttpResponse.BodyHandlers.ofByteArray())
            .thenApplyAsync { response: HttpResponse<ByteArray> -> response.log() }
            .thenApply { response: HttpResponse<ByteArray> -> response.parse(responseType) }
    }

    private fun <T> post(uri: URI, request: Request<T>): CompletableFuture<T>  {
        val bodyByteArray = convertToByteArray(request)
        logRequest(bodyByteArray)
        return HttpClient.newHttpClient()
            .sendAsync(buildRequest(uri), HttpResponse.BodyHandlers.ofByteArray())
            .thenApplyAsync { response: HttpResponse<ByteArray> -> response.log() }
            .thenApply { response: HttpResponse<ByteArray> -> response.parse(request.responseType) }
    }

    private fun <T> getSync(uri: URI, responseType: TypeReference<T>): T {
        return HttpClient.newHttpClient()
            .send(buildRequest(uri), HttpResponse.BodyHandlers.ofByteArray())
            .parse(responseType)
    }

    private fun <T> postSync(uri: URI, request: Request<T>): T  {
        val bodyByteArray = convertToByteArray(request)
        logRequest(bodyByteArray)
        return HttpClient.newHttpClient()
            .send(buildRequest(uri, bodyByteArray), HttpResponse.BodyHandlers.ofByteArray())
            .log()
            .parse(request.responseType)
    }

    private fun buildRequest(uri: URI, body: ByteArray? = null): HttpRequest? {
        val builder = HttpRequest.newBuilder()
            .headers(
                "Content-Type", DEFAULT_MIME_TYPE,
                "Accept", DEFAULT_MIME_TYPE)
        body?.let { builder.POST(HttpRequest.BodyPublishers.ofByteArray(body)) }
        return builder
            .timeout(DEFAULT_TIMEOUT)
            .uri(uri)
            .build()
    }

    private fun determineUri(request: Request<*>): URI {
        return URI.create(if (request.needToken) "$host/$mode$token/${request.method}" else "$host/${request.method}")
    }

    private fun logRequest(byteArray: ByteArray) {
        if (isDebugEnabled) {
            log.debug("Request data\n${String(byteArray)}")
        }
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
            }.also{log.info("State: $it")}.result!!.authorizationState
        }
        tokenFile.writeText(token!!)
    }

}