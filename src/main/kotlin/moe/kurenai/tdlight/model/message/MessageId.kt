package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.OptionalLong

data class MessageId(
    @JsonProperty("message_id")
    val messageId: Long? = null
) {
}