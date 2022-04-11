package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

class ChatMemberLeft : ChatMember() {
    @JsonProperty("status")
    var status: String = "left"

    @JsonProperty("user")
    var user: User? = null
}