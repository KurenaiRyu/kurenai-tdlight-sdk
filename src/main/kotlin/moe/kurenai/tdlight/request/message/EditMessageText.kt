package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.message.ParseMode
import moe.kurenai.tdlight.request.Request

data class EditMessageText(
    override val text: String,
    val disableWebPagePreview: Boolean? = null, override val parseMode: ParseMode? = null, override val entities: MessageEntity? = null,
    override val chatId: String? = null,
    override val messageId: Long? = null,
    override val inlineMessageId: String? = null,
) : Request<ResponseWrapper<Message>>(), EditMessageRequest, WithText {

    @JsonIgnore
    override val method = "editMessageText"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


