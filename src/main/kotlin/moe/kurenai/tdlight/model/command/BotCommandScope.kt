package moe.kurenai.tdlight.model.command

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author Kurenai
 * @since 2023/5/11 19:42
 */

class BotCommandScope(
    val type: BotCommandScopeType,
    /**
     * Chat id
     *
     * Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
     */
    @JsonProperty("chat_id")
    val chatId: String? = null,
    /**
     * User id
     *
     * Unique identifier of the target user
     */
    val userId: String? = null
)

enum class BotCommandScopeType(
    @get:JsonValue val value: String
) {
    DEFAULT("default"),
    ALL_PRIVATE_CHATS("all_private_chats"),
    ALL_GROUP_CHATS("all_group_chats"),
    ALL_CHAT_ADMINISTRATORS("all_chat_administrators"),
    CHAT("chat"),
    CHAT_MEMBER("chat_member"),
    CHAT_ADMINISTRATORS("chat_administrators"),
}