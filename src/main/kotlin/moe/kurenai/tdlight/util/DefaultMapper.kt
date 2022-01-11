package moe.kurenai.tdlight.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.logging.log4j.LogManager
import java.io.IOException
import java.net.http.HttpResponse


object DefaultMapper{

    private val log = LogManager.getLogger()

    val MAPPER: ObjectMapper = jsonMapper {
        addModules(kotlinModule(), Jdk8Module(), JavaTimeModule())
    }
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .setSerializationInclusion(JsonInclude.Include.NON_ABSENT)

    val MAPPER_WITH_ALL: ObjectMapper = jsonMapper {
        addModules(kotlinModule(), Jdk8Module(), JavaTimeModule())
    }
        .enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
        .setSerializationInclusion(JsonInclude.Include.ALWAYS)


    fun <T> convertToString(t: T): String {
        return try {
            MAPPER.writeValueAsString(t)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun <T> convertToByteArray(t: T): ByteArray {
        return try {
            MAPPER.writeValueAsBytes(t)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun <T> convertMapValueToStringJson(map: MutableMap<Any, Any>, keyName: String, t: T) {
        map.computeIfPresent(keyName) { _: Any, _: Any -> convertToString(t) }
    }

    fun <T> parseResponse(response: ByteArray, reference: TypeReference<T>): T {
        return try {
            MAPPER.readValue(response, reference)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun <T> convertToMap(t: T): Map<Any, Any?> {
        return MAPPER_WITH_ALL.convertValue(t, object : TypeReference<Map<Any, Any?>>() {})
    }

    fun <T> HttpResponse<ByteArray>.parse(reference: TypeReference<T>): T {
        return parseResponse(this.body(), reference)
    }
}