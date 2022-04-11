package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.MessageEntity

class InputMediaVideo(
    override val media: InputFile,
) : InputMedia, WithCaption{
    var duration: Long? = null
    var width: Int? = null
    var height: Int? = null
    var thumb: InputFile? = null
    var supportsStreaming: Boolean? = null

    override var parseMode: String? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null

    override val type = InputMediaType.VIDEO

}