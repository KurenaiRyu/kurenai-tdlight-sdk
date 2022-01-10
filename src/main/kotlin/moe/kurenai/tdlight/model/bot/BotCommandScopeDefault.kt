package moe.kurenai.tdlight.model.bot

class BotCommandScopeDefault : BotCommandScope {
    override val type: String
        get() = Companion.type

    companion object {
        private const val type = "default"
    }
}