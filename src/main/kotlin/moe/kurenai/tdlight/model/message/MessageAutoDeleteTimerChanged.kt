package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class MessageAutoDeleteTimerChanged(
    @JsonProperty("message_auto_delete_time")
    val messageAutoDeleteTime: Long = 0
) {
}