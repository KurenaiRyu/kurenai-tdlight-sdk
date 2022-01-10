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

class Venue {
    @JsonProperty("location")
    var location: Location? = null

    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("address")
    var address: String? = null

    @JsonProperty("foursquare_id")
    var foursquareId: Optional<String>? = null

    @JsonProperty("foursquare_type")
    var foursquareType: Optional<String>? = null

    @JsonProperty("google_place_id")
    var googlePlaceId: Optional<String>? = null

    @JsonProperty("google_place_type")
    var googlePlaceType: Optional<String>? = null
}