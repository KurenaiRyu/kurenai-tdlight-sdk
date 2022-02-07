package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User
import java.util.*

class ChatMemberOwner : ChatMember {
    @JsonProperty("status")
    var status: String = "creator"

    @JsonProperty("user")
    var user: User? = null

    @JsonProperty("is_anonymous")
    var isAnonymous = false

    @JsonProperty("custom_title")
    var customTitle: Optional<String>? = null
}