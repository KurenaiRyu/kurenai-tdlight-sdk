package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

data class ChatMemberAdministrator(

    @JsonProperty("status")
    val status: String,

    @JsonProperty("user")
    val user: User,

    @JsonProperty("can_be_edited")
    val canBeEdited: Boolean = false,

    @JsonProperty("is_anonymous")
    val isAnonymous: Boolean = false,

    @JsonProperty("can_manage_chat")
    val canManageChat: Boolean = false,

    @JsonProperty("can_delete_messages")
    val canDeleteMessages: Boolean = false,

    @JsonProperty("can_manage_voice_chats")
    val canManageVoiceChats: Boolean = false,

    @JsonProperty("can_restrict_members")
    val canRestrictMembers: Boolean = false,

    @JsonProperty("can_promote_members")
    val canPromoteMembers: Boolean = false,

    @JsonProperty("can_change_info")
    val canChangeInfo: Boolean = false,

    @JsonProperty("can_invite_users")
    val canInviteUsers: Boolean = false,

    @JsonProperty("can_post_messages")
    val canPostMessages: Boolean = false,

    @JsonProperty("can_edit_messages")
    val canEditMessages: Boolean = false,

    @JsonProperty("can_pin_messages")
    val canPinMessages: Boolean = false,

    @JsonProperty("custom_title")
    val customTitle: String? = null,
) : ChatMember {
}