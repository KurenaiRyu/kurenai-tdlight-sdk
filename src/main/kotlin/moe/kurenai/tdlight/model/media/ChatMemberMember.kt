package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

class ChatMemberMember : ChatMember() {
    @JsonProperty("status")
    var status: String = "member"

    @JsonProperty("user")
    var user: User? = null
}