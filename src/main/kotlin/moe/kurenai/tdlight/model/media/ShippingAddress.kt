package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.Optional
import moe.kurenai.tdlight.model.message.MessageEntity
import moe.kurenai.tdlight.model.media.Animation
import java.util.OptionalInt
import moe.kurenai.tdlight.model.encrypt.EncryptedPassportElement
import moe.kurenai.tdlight.model.encrypt.EncryptedCredentials
import moe.kurenai.tdlight.model.media.ShippingAddress
import moe.kurenai.tdlight.model.media.OrderInfo

class ShippingAddress {
    @JsonProperty("country_code")
    var countryCode: String? = null

    @JsonProperty("state")
    var state: String? = null

    @JsonProperty("city")
    var city: String? = null

    @JsonProperty("street_line1")
    var streetLine1: String? = null

    @JsonProperty("street_line2")
    var streetLine2: String? = null

    @JsonProperty("post_code")
    var postCode: String? = null
}