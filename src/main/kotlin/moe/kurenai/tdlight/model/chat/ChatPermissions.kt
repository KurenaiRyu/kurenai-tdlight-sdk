package moe.kurenai.tdlight.model.chat

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class ChatPermissions(

    @JsonProperty("can_send_messages")
    val canSendMessages: Boolean? = null,

    @JsonProperty("can_send_media_messages")
    val canSendMediaMessages: Boolean? = null,

    @JsonProperty("can_send_polls")
    val canSendPolls: Boolean? = null,

    @JsonProperty("can_send_other_messages")
    val canSendOtherMessages: Boolean? = null,

    @JsonProperty("can_add_web_page_previews")
    val canAddWebPagePreviews: Boolean? = null,

    @JsonProperty("can_change_info")
    val canChangeInfo: Boolean? = null,

    @JsonProperty("can_invite_users")
    val canInviteUsers: Boolean? = null,

    @JsonProperty("can_pin_messages")
    val canPinMessages: Boolean? = null,
) {
}