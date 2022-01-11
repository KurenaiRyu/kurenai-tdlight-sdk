package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.message.ParseMode
import moe.kurenai.tdlight.request.Request

data class EditMessageCaption(
    override val caption: String, override val captionEntities: MessageEntity? = null, override val parseMode: ParseMode? = null,
    override val chatId: String? = null,
    override val messageId: Long? = null,
    override val inlineMessageId: String? = null,
) : Request<ResponseWrapper<Message>>(), EditMessageRequest, WithCaption {

    @JsonIgnore
    override val method = "editMessageCaption"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


