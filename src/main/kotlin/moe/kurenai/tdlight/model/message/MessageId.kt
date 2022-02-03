package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class MessageId(
    @JsonProperty("message_id")
    val messageId: Long? = null
) {
}