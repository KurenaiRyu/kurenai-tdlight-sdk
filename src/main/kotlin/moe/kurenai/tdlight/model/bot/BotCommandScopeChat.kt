package moe.kurenai.tdlight.model.bot

import com.fasterxml.jackson.annotation.JsonProperty

data class BotCommandScopeChat(
    @JsonProperty("chat_id") override val chatId: Long?
) : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "chat"
    }
}