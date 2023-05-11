package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ReplyMarkup
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.request.Request

data class EditMessageText(
    override val text: String,
) : Request<ResponseWrapper<Message>>(), EditMessageRequest, WithText, WithReplyMarkup {

    var disableWebPagePreview: Boolean? = null
    override var parseMode: String? = null
    override var chatId: String? = null
    override var messageId: Long? = null
    override var inlineMessageId: String? = null

    override var entities: List<MessageEntity>? = null
    override var replyMarkup: ReplyMarkup? = null

    @JsonIgnore
    override val method = "editMessageText"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


