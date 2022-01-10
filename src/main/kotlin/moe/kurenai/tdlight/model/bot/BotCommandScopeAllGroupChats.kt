package moe.kurenai.tdlight.model.bot

class BotCommandScopeAllGroupChats : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "all_group_chats"
    }
}