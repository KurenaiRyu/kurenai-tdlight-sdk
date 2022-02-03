package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.encrypt.EncryptedCredentials
import moe.kurenai.tdlight.model.encrypt.EncryptedPassportElement

@NoArg
class PassportData {
    @JsonProperty("data")
    var data: List<EncryptedPassportElement>? = null

    @JsonProperty("credentials")
    var credentials: EncryptedCredentials? = null
}