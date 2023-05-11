package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ReplyMarkup
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.request.Request

data class SendMessage(
    override val chatId: String,
    override val text: String,
) : Request<ResponseWrapper<Message>>(), SendMessageRequest, WithText, WithReplyMarkup, Reply {
    var disableWebPagePreview: Boolean? = null
    override var parseMode: String? = null
    override var replyToMessageId: Long? = null
    override var allowSendingWithoutReply: Boolean? = null
    override var disableNotification: Boolean? = null
    override var sendAt: Long? = null
    override var replyMarkup: ReplyMarkup? = null

    override var entities: List<MessageEntity>? = null

    @JsonIgnore
    override val method = "sendMessage"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


