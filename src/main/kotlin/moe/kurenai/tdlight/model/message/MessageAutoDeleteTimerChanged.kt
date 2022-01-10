package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty

data class MessageAutoDeleteTimerChanged(
    @JsonProperty("message_auto_delete_time")
    val messageAutoDeleteTime: Long = 0
) {
}