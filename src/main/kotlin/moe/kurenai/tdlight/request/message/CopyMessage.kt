package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ReplyMarkup
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.request.Request

data class CopyMessage(
    override val chatId: String,
    val fromChatId: String,
    val messageId: Long,
) : Request<ResponseWrapper<Message>>(), SendMessageRequest, WithCaption, Reply, WithReplyMarkup {
    override var replyToMessageId: Int? = null
    override var allowSendingWithoutReply: Boolean? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null
    override var replyMarkup: ReplyMarkup? = null
    override var parseMode: String? = null
    override var disableNotification: Boolean? = null
    override var sendAt: Long? = null

    @JsonIgnore
    override val method = "copyMessage"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


