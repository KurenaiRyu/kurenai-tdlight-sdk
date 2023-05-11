package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class MessageEntity(

    @JsonProperty("type")
    var type: String,

    @JsonProperty("offset")
    var offset: Int,

    @JsonProperty("length")
    var length: Int,
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