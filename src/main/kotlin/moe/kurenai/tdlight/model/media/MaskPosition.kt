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

class MaskPosition {
    @JsonProperty("point")
    var point: String? = null

    @JsonProperty("x_shift")
    var xShift = 0f

    @JsonProperty("y_shift")
    var yShift = 0f

    @JsonProperty("scale")
    var scale = 0f
}