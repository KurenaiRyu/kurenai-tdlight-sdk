package moe.kurenai.tdlight.model.poll

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.User

@NoArg
data class PollAnswer(

    @JsonProperty("poll_id")
    val pollId: String,

    @JsonProperty("user")
    val user: User,

    @JsonProperty("option_ids")
    val optionIds: List<Int>,
) {
}