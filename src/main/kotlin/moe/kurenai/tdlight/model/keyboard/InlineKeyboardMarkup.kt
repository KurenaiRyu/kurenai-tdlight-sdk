package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.ReplyMarkup

@NoArg
data class InlineKeyboardMarkup(

    @JsonProperty("inline_keyboard")
    var inlineKeyboard: List<List<InlineKeyboardButton>>? = null
) : ReplyMarkup {
}