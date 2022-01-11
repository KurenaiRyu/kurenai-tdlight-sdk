package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.request.Request

data class ForwardMessage(
    override val chatId: String,
    val fromChatId: String,
    val messageId: Long, override val disableNotification: Boolean? = null, override val sendAt: Long? = null
) : Request<ResponseWrapper<Message>>(), SendMessageRequest {

    @JsonIgnore
    override val method = "forwardMessage"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


