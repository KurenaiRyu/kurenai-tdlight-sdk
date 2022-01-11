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

data class SendVideo(
    override val chatId: String,
    val video: InputFile,
    val duration: Long? = null,
    val width: Int? = null,
    val height: Int? = null,
    val thumb: InputFile? = null,
    val supportsStreaming: Boolean? = null,
    override val parseMode: ParseMode? = null,
    override var replyToMessageId: Long? = null,
    override var allowSendingWithoutReply: Boolean? = null,
    override val disableNotification: Boolean? = null,
    override val sendAt: Long? = null,
    override val caption: String? = null,
    override val captionEntities: MessageEntity? = null,
    override val replyMarkup: ReplyMarkup? = null,
) : Request<ResponseWrapper<Message>>(), SendMessageRequest, MediaRequest, Reply, WithCaption, WithReplyMarkup {

    @JsonIgnore
    override val method = "sendVideo"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Message>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST

}


