package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.media.Location

data class ChatLocation(

    @JsonProperty("location")
    val location: Location,

    @JsonProperty("address")
    val address: String,
) {
}