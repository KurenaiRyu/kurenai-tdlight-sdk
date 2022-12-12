package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardMarkup
import moe.kurenai.tdlight.model.message.MessageEntity

@NoArg
class InlineQueryResultMpeg4Gif(
    id: String,
) : InlineQueryResult("mpeg4_gif", id) {

    var title: String? = null
    var mpeg4Url: String? = null
    var thumbUrl: String? = null
    var thumbMimeType: String? = null
    var mpeg4Width: Int? = null
    var mpeg4Height: Int? = null
    var mpeg4Duration: Int? = null
    var description: String? = null
    var caption: String? = null
    var parseMode: String? = null
    var captionEntities: List<MessageEntity>? = null
    var replyMarkup: InlineKeyboardMarkup? = null
    var inputMessageContent: InputMessageContent? = null

}