package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.media.Location

@NoArg
data class ChatLocation(

    @JsonProperty("location")
    val location: Location,

    @JsonProperty("address")
    val address: String,
) {
}