package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.Message
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.message.ParseMode
import moe.kurenai.tdlight.model.message.ReplyMarkup
import moe.kurenai.tdlight.request.MediaRequest
import moe.kurenai.tdlight.request.Request

class SendPhoto(
    override val chatId: String,
    val photo: InputFile,
    override var replyToMessageId: Long? = null,
    override var allowSendingWithoutReply: Boolean? = null,
    override val caption: String? = null,
    override val captionEntities: MessageEntity? = null,
    override val replyMarkup: ReplyMarkup? = null,
    override val parseMode: ParseMode? = null,
    override val disableNotification: Boolean? = null,
    override val sendAt: Long? = null,
) : Request<ResponseWrapper<Message>>(), SendMessageRequest, MediaRequest, Reply, WithCaption, WithReplyMarkup {

    @JsonIgnore
    override val method = "sendPhoto"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


