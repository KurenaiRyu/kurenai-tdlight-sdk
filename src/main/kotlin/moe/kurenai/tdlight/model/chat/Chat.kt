package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.Message

data class Chat(

    @JsonProperty("id")
    val id: Long,

    @JsonProperty("type")
    val type: ChatType,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("username")
    val username: String? = null,

    @JsonProperty("first_name")
    val firstName: String? = null,

    @JsonProperty("last_name")
    val lastName: String? = null,

    @JsonProperty("photo")
    val photo: ChatPhoto? = null,

    @JsonProperty("bio")
    val bio: String? = null,

    @JsonProperty("has_private_forwards")
    val hasPrivateForwards: Boolean = false,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("invite_link")
    val inviteLink: String? = null,

    @JsonProperty("pinned_message")
    val pinnedMessage: Message? = null,

    @JsonProperty("permissions")
    val permissions: ChatPermissions? = null,

    @JsonProperty("slow_mode_delay")
    val slowModeDelay: Int? = null,

    @JsonProperty("message_auto_delete_time")
    val messageAutoDeleteTime: Int? = null,

    @JsonProperty("has_protected_content")
    val hasProtectedContent: Boolean = false,

    @JsonProperty("sticker_set_name")
    val stickerSetName: String? = null,

    @JsonProperty("can_set_sticker_set")
    val canSetStickerSet: Boolean? = null,

    @JsonProperty("linked_chat_id")
    val linkedChatId: Int? = null,

    @JsonProperty("location")
    val location: ChatLocation? = null,
) {
}