package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import java.util.OptionalInt

data class VoiceChatScheduled(

    @JsonProperty("start_date")
    val startDate: Long = 0
) {
}