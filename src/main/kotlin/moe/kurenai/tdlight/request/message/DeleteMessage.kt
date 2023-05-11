package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.request.Request

data class DeleteMessage(
    val chatId: String,
    val messageId: Long,
) : Request<ResponseWrapper<Boolean>>() {

    @JsonIgnore
    override val method = "deleteMessage"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Boolean>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


