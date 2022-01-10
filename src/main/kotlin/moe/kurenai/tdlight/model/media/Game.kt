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

class Game {
    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("photo")
    var photo: List<PhotoSize>? = null

    @JsonProperty("text")
    var text: Optional<String>? = null

    @JsonProperty("text_entities")
    var textEntities: List<MessageEntity>? = null

    @JsonProperty("animation")
    var animation: Optional<Animation>? = null
}