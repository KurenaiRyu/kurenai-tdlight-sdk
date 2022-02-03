package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.request.MediaRequest
import moe.kurenai.tdlight.request.Request

data class EditMessageMedia(
    val media: InputFile,
    val disableWebPagePreview: Boolean? = null, override val chatId: String? = null, override val messageId: Int? = null, override val inlineMessageId: String? = null,
) : Request<ResponseWrapper<Message>>(), EditMessageRequest, MediaRequest {

    @JsonIgnore
    override val method = "editMessageText"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


