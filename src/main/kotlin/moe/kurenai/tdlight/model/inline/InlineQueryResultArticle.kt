package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardMarkup
import moe.kurenai.tdlight.model.message.MessageEntity

@NoArg
class InlineQueryResultArticle(
    id: String,
    var title: String
) : InlineQueryResult("article", id) {

    var url: String? = null
    var hideUrl: Boolean = false
    var description: String? = null
    var thumbUrl: String? = null
    var thumbWidth: Int? = null
    var thumbHeight: Int? = null
    var parseMode: String? = null
    var captionEntities: List<MessageEntity>? = null
    var replyMarkup: InlineKeyboardMarkup? = null
    var inputMessageContent: InputMessageContent? = null

}