package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.message.MessageEntity

interface WithCaption : Parseable {

    var caption: String?
    var captionEntities: List<MessageEntity>?

}