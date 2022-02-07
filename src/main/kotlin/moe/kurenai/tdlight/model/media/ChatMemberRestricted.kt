package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.model.message.User

class ChatMemberRestricted : ChatMember {
    @JsonProperty("status")
    var status: String = "restricted"

    @JsonProperty("user")
    var user: User? = null

    @JsonProperty("is_member")
    var isMember = false

    @JsonProperty("can_change_info")
    var canChangeInfo = false

    @JsonProperty("can_invite_users")
    var canInviteUsers = false

    @JsonProperty("can_pin_messages")
    var canPinMessages = false

    @JsonProperty("can_send_messages")
    var canSendMessages = false

    @JsonProperty("can_send_media_messages")
    var canSendMediaMessages = false

    @JsonProperty("can_send_polls")
    var canSendPolls = false

    @JsonProperty("can_send_other_messages")
    var canSendOtherMessages = false

    @JsonProperty("can_add_web_page_previews")
    var canAddWebPagePreviews = false

    @JsonProperty("until_date")
    var untilDate: Long = 0
}