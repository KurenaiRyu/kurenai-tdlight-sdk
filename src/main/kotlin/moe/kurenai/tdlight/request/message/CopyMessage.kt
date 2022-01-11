package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.message.ParseMode
import moe.kurenai.tdlight.model.message.ReplyMarkup
import moe.kurenai.tdlight.request.Request

data class CopyMessage(
    override val chatId: String,
    val fromChatId: String,
    val messageId: Long,
    override var replyToMessageId: Long? = null,
    override var allowSendingWithoutReply: Boolean? = null,
    override val caption: String? = null,
    override val captionEntities: MessageEntity? = null,
    override val replyMarkup: ReplyMarkup? = null,
    override val parseMode: ParseMode? = null,
    override val disableNotification: Boolean? = null,
    override val sendAt: Long? = null
) : Request<ResponseWrapper<Message>>(), SendMessageRequest, WithCaption, Reply, WithReplyMarkup {

    @JsonIgnore
    override val method = "copyMessage"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


