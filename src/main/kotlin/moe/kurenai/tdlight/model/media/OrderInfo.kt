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

class OrderInfo {
    @JsonProperty("name")
    var name: Optional<String>? = null

    @JsonProperty("phone_number")
    var phone_number: Optional<String>? = null

    @JsonProperty("email")
    var email: Optional<String>? = null

    @JsonProperty("shipping_address")
    var shippingAddress: Optional<ShippingAddress>? = null
}