package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class MessageEntity(

    @JsonProperty("type")
    val type: MessageEntityType,

    @JsonProperty("offset")
    val offset: Int,

    @JsonProperty("length")
    val length: Int,

    @JsonProperty("url")
    val url: String? = null,

    @JsonProperty("user")
    val user: User? = null,

    @JsonProperty("language")
    val language: String? = null,
) {
    @JsonIgnore
    lateinit var text: String
}