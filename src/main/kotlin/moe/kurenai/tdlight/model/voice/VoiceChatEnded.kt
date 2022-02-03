package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class VoiceChatEnded(

    @JsonProperty("duration")
    var duration: Long = 0
) {
}