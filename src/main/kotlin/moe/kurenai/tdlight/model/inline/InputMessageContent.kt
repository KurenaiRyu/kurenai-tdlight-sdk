package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.model.message.MessageEntity

abstract class InputMessageContent {

    var parseMode: String? = null
    var entities: List<MessageEntity>? = null

}