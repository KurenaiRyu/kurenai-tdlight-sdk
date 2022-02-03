package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import java.util.*

@NoArg
data class LoginUrl(

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("forward_text")
    val forwardText: Optional<String>? = null,

    @JsonProperty("bot_username")
    val botUsername: Optional<String>? = null,

    @JsonProperty("request_write_access")
    val requestWriteAccess: Optional<Boolean>? = null,
) {
}