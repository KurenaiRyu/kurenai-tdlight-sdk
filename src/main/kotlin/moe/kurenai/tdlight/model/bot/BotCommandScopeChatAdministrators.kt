package moe.kurenai.tdlight.model.bot

data class BotCommandScopeChatAdministrators(
    override val chatId: Long?
) : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "chat_administrators"
    }
}