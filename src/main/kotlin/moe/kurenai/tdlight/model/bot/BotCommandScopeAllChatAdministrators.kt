package moe.kurenai.tdlight.model.bot

class BotCommandScopeAllChatAdministrators : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "all_chat_administrators"
    }
}