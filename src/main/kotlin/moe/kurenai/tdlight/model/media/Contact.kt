package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt
import moe.kurenai.tdlight.model.chat.ChatMember

class Contact {
    @JsonProperty("phone_number")
    var phoneNumber: String? = null

    @JsonProperty("first_name")
    var firstName: String? = null

    @JsonProperty("last_name")
    var lastName: Optional<String>? = null

    @JsonProperty("user_id")
    var userId: OptionalInt? = null

    @JsonProperty("vcard")
    var vcard: Optional<String>? = null
}