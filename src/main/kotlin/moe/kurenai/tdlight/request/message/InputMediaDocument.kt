package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.MessageEntity

class InputMediaDocument(
    override val media: InputFile,
) : InputMedia, WithCaption{
    var thumb: InputFile? = null

    var disable_content_type_detection: Boolean? = null

    override var parseMode: String? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null

    override val type = InputMediaType.DOCUMENT

}