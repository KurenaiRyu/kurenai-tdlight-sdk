package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.message.MessageEntity

interface WithCaption : Parseable {

    val caption: String?
    val captionEntities: MessageEntity?

}