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
import moe.kurenai.tdlight.model.message.User

class ProximityAlertTriggered {
    @JsonProperty("traveler")
    var traveler: User? = null

    @JsonProperty("watcher")
    var watcher: User? = null

    @JsonProperty("distance")
    var distance: User? = null
}