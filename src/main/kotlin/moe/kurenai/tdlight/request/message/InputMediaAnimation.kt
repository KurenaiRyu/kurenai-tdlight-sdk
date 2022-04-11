package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.MessageEntity

class InputMediaAnimation(
    override val media: InputFile,
) : InputMedia, WithCaption{
    var duration: Long? = null
    var width: Int? = null
    var height: Int? = null
    var thumb: InputFile? = null

    override var parseMode: String? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null

    override val type = InputMediaType.ANIMATION

}