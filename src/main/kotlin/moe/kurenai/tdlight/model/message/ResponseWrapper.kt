package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
data class ResponseWrapper<T>(

    @JsonProperty("ok")
    val ok: Boolean = false,

    @JsonProperty("result")
    val result: T? = null,

    @JsonProperty("error_code")
    val errorCode: String? = null,

    @JsonProperty("description")
    val description: String? = null,
) {
}