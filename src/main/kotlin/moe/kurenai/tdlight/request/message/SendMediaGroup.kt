package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.request.MediaRequest
import moe.kurenai.tdlight.request.Request

data class SendMediaGroup(
    override val chatId: String,
) : Request<ResponseWrapper<List<Message>>>(), SendMessageRequest, Reply, MediaRequest {
    @JsonIgnore
    var media: List<InputMedia>? = null
    override var replyToMessageId: Long? = null
    override var allowSendingWithoutReply: Boolean? = null
    override var disableNotification: Boolean? = null
    override var sendAt: Long? = null

    @JsonIgnore
    override val method = "sendMediaGroup"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<List<Message>>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


