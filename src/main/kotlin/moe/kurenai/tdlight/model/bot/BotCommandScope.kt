package moe.kurenai.tdlight.model.bot

interface BotCommandScope {
    val type: String?
    val chatId: Long?
        get() = null
    val userId: Long?
        get() = null
}