package moe.kurenai.tdlight.model.bot

class BotCommandScopeAllPrivateChats : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "all_private_chats"
    }
}