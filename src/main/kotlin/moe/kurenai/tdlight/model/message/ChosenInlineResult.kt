package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.media.Location

data class ChosenInlineResult(

    @JsonProperty("result_id")
    val resultId: String,

    @JsonProperty("from")
    val from: User,

    @JsonProperty("location")
    val location: Location? = null,

    @JsonProperty("inline_message_id")
    val inlineMessageId: String? = null,

    @JsonProperty("query")
    val query: String? = null,
)