package moe.kurenai.tdlight.model.inline

import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardMarkup
import moe.kurenai.tdlight.model.message.MessageEntity

@NoArg
class InlineQueryResultVideo(
    id: String,
    var title: String
) : InlineQueryResult("video", id) {

    var videoUrl: String? = null
    var mimeType: String? = MIMEType.MP4
    var thumbUrl: String? = null
    var thumbMimeType: String? = null
    var videoWidth: Int? = null
    var videoHeight: Int? = null
    var videoDuration: Int? = null
    var description: String? = null
    var caption: String? = null
    var parseMode: String? = null
    var captionEntities: List<MessageEntity>? = null
    var replyMarkup: InlineKeyboardMarkup? = null
    var inputMessageContent: InputMessageContent? = null

}