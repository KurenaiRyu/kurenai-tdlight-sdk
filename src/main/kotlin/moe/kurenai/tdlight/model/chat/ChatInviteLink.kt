package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.message.User

data class ChatInviteLink(

    @JsonProperty("invite_link")
    val inviteLink: String,

    @JsonProperty("creator")
    val creator: User,

    @JsonProperty("creates_join_request")
    val createsJoinRequest: Boolean = false,

    @JsonProperty("is_primary")
    val isPrimary: Boolean = false,

    @JsonProperty("is_revoked")
    val isRevoked: Boolean = false,

    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("expire_date")
    val expireDate: Long? = null,

    @JsonProperty("member_limit")
    val memberLimit: Int? = null,

    @JsonProperty("pending_join_request_count")
    val pendingJoinRequestCount: Int? = null,
) {
}