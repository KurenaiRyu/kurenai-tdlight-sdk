package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional

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