package moe.kurenai.tdlight.model.bot

import com.fasterxml.jackson.annotation.JsonProperty

data class BotCommand(

    @JsonProperty("command")
    val command: String,

    @JsonProperty("description")
    val description: String,
) {
}