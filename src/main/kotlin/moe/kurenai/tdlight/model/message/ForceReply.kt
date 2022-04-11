package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.ReplyMarkup

@NoArg
data class ForceReply(

    @JsonProperty("force_reply")
    val forceReply: Boolean,

    @JsonProperty("input_field_placeholder")
    val inputFieldPlaceholder: String? = null,

    @JsonProperty("selective")
    val selective: Boolean? = null,
) : ReplyMarkup