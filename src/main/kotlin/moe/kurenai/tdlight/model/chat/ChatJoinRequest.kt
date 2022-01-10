package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.User

data class ChatJoinRequest(

    @JsonProperty("chat")
    val chat: Chat,

    @JsonProperty("from")
    val from: User,

    @JsonProperty("date")
    val date: Long = 0,

    @JsonProperty("bio")
    val bio: String? = null,

    @JsonProperty("invite_link")
    val inviteLink: ChatInviteLink? = null,
) {
}