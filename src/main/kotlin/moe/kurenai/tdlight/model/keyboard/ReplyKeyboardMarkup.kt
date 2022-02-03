package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.ReplyMarkup

@NoArg
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