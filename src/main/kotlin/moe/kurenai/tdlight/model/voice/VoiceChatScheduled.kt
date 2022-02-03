package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class VoiceChatScheduled(

    @JsonProperty("start_date")
    val startDate: Long = 0
) {
}