package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.message.ReplyMarkup

interface WithReplyMarkup {

    val replyMarkup: ReplyMarkup?
}