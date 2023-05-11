package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.request.Request

data class DeleteMessages(
    val chatId: String,
    val start: Long,
    val end: Long,
) : Request<ResponseWrapper<Boolean>>() {

    @JsonIgnore
    override val method = "deleteMessages"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Boolean>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


