package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.ReplyMarkup

@NoArg
data class ReplyKeyboardRemove(

    @JsonProperty("remove_keyboard")
    val removeKeyboard: Boolean,

    @JsonProperty("selective")
    val selective: Boolean? = null
) : ReplyMarkup {
}