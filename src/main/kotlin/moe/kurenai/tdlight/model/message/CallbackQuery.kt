package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty

data class CallbackQuery(

    @JsonProperty("id")
    val id: String,

    @JsonProperty("from")
    val from: User,

    @JsonProperty("message")
    val message: Message?,

    @JsonProperty("inline_message_id")
    val inlineMessageId: String? = null,

    @JsonProperty("chat_instance")
    val chatInstance: String,

    @JsonProperty("data")
    val data: String?,

    @JsonProperty("game_short_name")
    val gameShortName: String? = null,
) {
}