package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.message.ParseMode

interface Parseable {

    val parseMode: ParseMode?
}