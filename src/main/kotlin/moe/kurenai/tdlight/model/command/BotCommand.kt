package moe.kurenai.tdlight.model.command

/**
 * @author Kurenai
 * @since 2023/5/11 20:10
 */

class BotCommand(
    /**
     * Text of the command; 1-32 characters. Can contain only lowercase English letters, digits and underscores.
     */
    val command: String,
    /**
     * Description of the command; 1-256 characters.
     */
    val description: String,
)