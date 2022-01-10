package moe.kurenai.tdlight.model.keyboard

import moe.kurenai.tdlight.model.message.ReplyMarkup
import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.keyboard.InlineKeyboardButton

data class InlineKeyboardMarkup(

    @JsonProperty("inline_keyboard")
    val inlineKeyboard: List<List<InlineKeyboardButton>>? = null
) : ReplyMarkup {
}