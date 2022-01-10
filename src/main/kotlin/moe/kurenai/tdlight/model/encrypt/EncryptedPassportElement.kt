package moe.kurenai.tdlight.model.encrypt

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.media.PassportFile

data class EncryptedPassportElement(

    @JsonProperty("type")
    val type: EncryptedCredentialsType,

    @JsonProperty("data")
    val data: String? = null,

    @JsonProperty("phone_number")
    val phoneNumber: String? = null,

    @JsonProperty("email")
    val email: String? = null,

    @JsonProperty("files")
    val files: List<PassportFile>,

    @JsonProperty("front_side")
    val frontSide: PassportFile? = null,

    @JsonProperty("reverse_side")
    val reverseSide: PassportFile? = null,

    @JsonProperty("selfie")
    val selfie: PassportFile? = null,

    @JsonProperty("translation")
    val translation: List<PassportFile>,

    @JsonProperty("hash")
    val hash: List<String>,
) {
}