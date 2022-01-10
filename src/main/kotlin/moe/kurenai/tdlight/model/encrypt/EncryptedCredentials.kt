package moe.kurenai.tdlight.model.encrypt

import com.fasterxml.jackson.annotation.JsonProperty

data class EncryptedCredentials(

    @JsonProperty("data")
    val data: String,

    @JsonProperty("hash")
    val hash: String,

    @JsonProperty("secret")
    val secret: String,
) {
}