package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class KeyboardButtonPollType(

    @JsonProperty("type")
    val type: String? = null
) {
}