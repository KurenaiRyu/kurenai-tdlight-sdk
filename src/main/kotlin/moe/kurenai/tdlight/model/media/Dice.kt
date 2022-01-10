package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt
import moe.kurenai.tdlight.model.chat.ChatMember

class Dice {
    @JsonProperty("emoji")
    var emoji: String? = null

    @JsonProperty("value")
    var value = 0
}