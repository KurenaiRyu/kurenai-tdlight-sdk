package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

data class ChatMemberBanned(

    @JsonProperty("status")
    val status: String = "kicked",

    @JsonProperty("user")
    val user: User,

    @JsonProperty("until_date")
    val untilDate: Long = 0,
) : ChatMember()