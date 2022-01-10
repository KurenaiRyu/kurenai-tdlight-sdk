package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty

data class KeyboardButton(

    @JsonProperty("text")
    var text: String,

    @JsonProperty("request_contact")
    var requestContact: Boolean? = null,

    @JsonProperty("request_location")
    var requestLocation: Boolean? = null,

    @JsonProperty("request_poll")
    var requestPoll: KeyboardButtonPollType? = null,
) {
}