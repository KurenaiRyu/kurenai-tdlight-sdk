package moe.kurenai.tdlight.model.keyboard

import moe.kurenai.tdlight.model.message.ReplyMarkup
import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.keyboard.KeyboardButton
import java.util.Optional

data class ReplyKeyboardMarkup(

    @JsonProperty("keyboard")
    val keyboard: List<List<KeyboardButton>>,

    @JsonProperty("resize_keyboard")
    val resizeKeyboard: Boolean? = null,

    @JsonProperty("one_time_keyboard")
    val oneTimeKeyboard: Boolean? = null,

    @JsonProperty("input_field_placeholder")
    val inputFieldPlaceholder: String? = null,

    @JsonProperty("selective")
    val selective: Boolean? = null,
) : ReplyMarkup {
}