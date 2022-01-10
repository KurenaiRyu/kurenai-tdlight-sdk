package moe.kurenai.tdlight.model.poll

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.poll.PollOption
import moe.kurenai.tdlight.model.poll.PollType
import java.util.OptionalInt
import moe.kurenai.tdlight.model.message.MessageEntity

data class Poll(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("question")
    val question: String,

    @JsonProperty("options")
    val options: List<PollOption>,

    @JsonProperty("total_voter_count")
    val totalVoterCount: Int = 0,

    @JsonProperty("is_closed")
    val isClosed: Boolean,

    @JsonProperty("is_anonymous")
    val isAnonymous: Boolean,

    @JsonProperty("type")
    val type: PollType,

    @JsonProperty("allows_multiple_answers")
    val allowsMultipleAnswers: Boolean,

    @JsonProperty("correct_option_id")
    val correctOptionId: Int? = null,

    @JsonProperty("explanation")
    val explanation: String,

    @JsonProperty("explanation_entities")
    val explanationEntities: List<MessageEntity>,

    @JsonProperty("open_period")
    val openPeriod: Int? = null,

    @JsonProperty("close_date")
    val closeDate: Int? = null,
) {
}