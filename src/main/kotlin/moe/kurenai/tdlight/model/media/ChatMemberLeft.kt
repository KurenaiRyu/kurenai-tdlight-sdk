package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

class ChatMemberLeft : ChatMember {
    @JsonProperty("status")
    var status: String? = null

    @JsonProperty("user")
    var user: User? = null
}