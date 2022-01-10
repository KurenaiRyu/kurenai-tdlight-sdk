package moe.kurenai.tdlight.model.keyboard

import moe.kurenai.tdlight.model.message.ReplyMarkup
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional

data class ReplyKeyboardRemove(

    @JsonProperty("remove_keyboard")
    val removeKeyboard: Boolean,

    @JsonProperty("selective")
    val selective: Boolean? = null
) : ReplyMarkup {
}