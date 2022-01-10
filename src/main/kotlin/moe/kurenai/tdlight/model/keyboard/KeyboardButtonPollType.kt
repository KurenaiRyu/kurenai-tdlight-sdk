package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional

data class KeyboardButtonPollType(

    @JsonProperty("type")
    val type: String? = null
) {
}