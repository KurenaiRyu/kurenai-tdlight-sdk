package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
class Dice {
    @JsonProperty("emoji")
    var emoji: String? = null

    @JsonProperty("value")
    var value = 0
}