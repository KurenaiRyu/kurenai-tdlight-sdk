package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageId(
    @JsonProperty("message_id")
    val messageId: Long? = null
) {
}