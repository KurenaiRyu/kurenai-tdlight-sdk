package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.message.MessageEntity

interface WithText : Parseable {

    val text: String
    val entities: List<MessageEntity>?

}