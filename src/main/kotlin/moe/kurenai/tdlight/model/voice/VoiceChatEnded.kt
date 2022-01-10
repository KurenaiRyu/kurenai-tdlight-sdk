package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import java.util.OptionalInt

data class VoiceChatEnded(

    @JsonProperty("duration")
    var duration: Long = 0
) {
}