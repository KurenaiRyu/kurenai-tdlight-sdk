package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile
import moe.kurenai.tdlight.model.message.MessageEntity

class InputMediaAudio(
    override val media: InputFile,
) : InputMedia, WithCaption{

    var thumb: InputFile? = null

    var duration: Long? = null
    var performer: String? = null
    var title: String? = null

    override var parseMode: String? = null
    override var caption: String? = null
    override var captionEntities: List<MessageEntity>? = null

    override val type = InputMediaType.AUDIO

}