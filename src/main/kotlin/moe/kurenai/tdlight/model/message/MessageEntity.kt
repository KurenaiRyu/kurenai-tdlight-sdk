package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class MessageEntity(

    @JsonProperty("type")
    val type: String,

    @JsonProperty("offset")
    val offset: Int,

    @JsonProperty("length")
    val length: Int,
) {
    @JsonIgnore
    var text: String? = null

    @JsonProperty("url")
    var url: String? = null

    @JsonProperty("user")
    var user: User? = null

    @JsonProperty("language")
    var language: String? = null
}