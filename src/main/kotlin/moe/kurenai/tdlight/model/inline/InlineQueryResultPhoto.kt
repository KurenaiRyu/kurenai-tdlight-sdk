package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardMarkup
import moe.kurenai.tdlight.model.message.MessageEntity

@NoArg
class InlineQueryResultPhoto(
    id: String,
) : InlineQueryResult("photo", id) {

    var title: String? = null
    var photoUrl: String = ""
    var thumbUrl: String = ""
    var photoWidth: Int? = null
    var photoHeight: Int? = null
    var description: String? = null
    var caption: String? = null
    var parseMode: String? = null
    var captionEntities: List<MessageEntity>? = null
    var replyMarkup: InlineKeyboardMarkup? = null
    var inputMessageContent: InputMessageContent? = null

}