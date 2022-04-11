package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.MessageEntity

class InputMediaPhoto(
    override val media: InputFile,
) : InputMedia, WithCaption {

    override var parseMode: String? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null

    override val type = InputMediaType.PHOTO

}