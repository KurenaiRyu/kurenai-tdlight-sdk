package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.User

data class ChatMemberUpdated(

    @JsonProperty("chat")
    val chat: Chat,

    @JsonProperty("from")
    val user: User,

    @JsonProperty("date")
    val date: Long = 0,

    @JsonProperty("old_chat_member")
    val oldChatMember: ChatMember,

    @JsonProperty("new_chat_member")
    val newChatMember: ChatMember,

    @JsonProperty("invite_link")
    val inviteLink: ChatInviteLink? = null,
) {
}