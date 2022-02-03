package moe.kurenai.tdlight.model.poll

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class PollOption(

    @JsonProperty("text")
    val text: String,

    @JsonProperty("voter_count")
    val voterCount: Int = 0,
) {
}