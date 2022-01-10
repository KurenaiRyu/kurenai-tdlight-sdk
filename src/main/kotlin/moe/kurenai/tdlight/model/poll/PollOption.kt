package moe.kurenai.tdlight.model.poll

import com.fasterxml.jackson.annotation.JsonProperty

data class PollOption(

    @JsonProperty("text")
    val text: String,

    @JsonProperty("voter_count")
    val voterCount: Int = 0,
) {
}