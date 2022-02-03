package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.User

@NoArg
data class VoiceChatParticipantsInvited(

    @JsonProperty("users")
    val users: List<User>
) {
}