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

class Location {
    @JsonProperty("longitude")
    var longitude = 0f

    @JsonProperty("latitude")
    var latitude = 0f

    @JsonProperty("horizontal_accuracy")
    var horizontalAccuracy: Optional<Float>? = null

    @JsonProperty("live_period")
    var livePeriod: OptionalInt? = null

    @JsonProperty("heading")
    var heading: OptionalInt? = null

    @JsonProperty("proximity_alert_radius")
    var proximityAlertRadius: OptionalInt? = null
}