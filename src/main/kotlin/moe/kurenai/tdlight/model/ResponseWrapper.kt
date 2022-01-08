package moe.kurenai.tdlight.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ResponseWrapper<T>(
    @JsonProperty("ok") val ok: Boolean,
    @JsonProperty("result") val result: T?,
    @JsonProperty("error_code") val errorCode: String?,
    @JsonProperty("description") val description: String?
)