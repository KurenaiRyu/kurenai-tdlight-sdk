package moe.kurenai.tdlight.model.bot

data class BotCommandScopeChatMember(
    override val chatId: Long?,
    override val userId: Long?,
) : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "chat_member"
    }
}