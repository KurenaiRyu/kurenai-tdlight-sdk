package moe.kurenai.tdlight.client

import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.LongPollingTelegramBot
import moe.kurenai.tdlight.request.Request
import moe.kurenai.tdlight.util.DefaultMapper.convertToByteArray
import moe.kurenai.tdlight.util.DefaultMapper.parse
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.concurrent.CompletableFuture

class UserClient(
    val host: String,
    val isDebugEnabled: Boolean = true
) {
    var token: String? = null

    companion object {
        private val log = LoggerFactory.getLogger(UserClient::class.java)
    }

    private val DEFAULT_TIMEOUT = Duration.ofSeconds(10)
    private val DEFAULT_MIME_TYPE = "application/json"

    fun <T> send(request: Request<T>): CompletableFuture<T> {
        val uri = URI.create(if (request.needToken) "$host/user$token/${request.method}" else "$host/${request.method}")
        log.info("Request to $uri")
        println("Request to $uri")

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
        log.info("Request to $uri")
        println("Request to $uri")

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
            .send(buildRequest(uri), HttpResponse.BodyHandlers.ofByteArray())
            .log()
            .parse(request.responseType)
    }

    private fun buildRequest(uri: URI): HttpRequest? {
        return HttpRequest.newBuilder()
            .header("Content-Type", DEFAULT_MIME_TYPE)
            .headers("Accept", DEFAULT_MIME_TYPE)
            .timeout(DEFAULT_TIMEOUT)
            .uri(uri)
            .build()
    }

    private fun determineUri(request: Request<*>): URI {
        return URI.create(if (request.needToken) "$host/user$token/${request.method}" else "$host/${request.method}")
    }

    private fun logRequest(byteArray: ByteArray) {
        if (isDebugEnabled) {
            log.debug("Request data\n${String(byteArray)}")
            println("Request data\n${String(byteArray)}")
        }
    }

    private fun logResponse(response: HttpResponse<ByteArray>): HttpResponse<ByteArray> {
        if (isDebugEnabled) response.body()?.let { log.debug("Response ${String(it)}") }
        return response
    }

    private fun HttpResponse<ByteArray>.log(): HttpResponse<ByteArray> {
        return logResponse(this)
    }

}