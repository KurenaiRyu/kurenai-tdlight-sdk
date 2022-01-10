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

class Video {
    @JsonProperty("file_id")
    var file_id: String? = null

    @JsonProperty("file_unique_id")
    var fileUniqueId: String? = null

    @JsonProperty("width")
    var width = 0

    @JsonProperty("height")
    var height = 0

    @JsonProperty("duration")
    var duration = 0

    @JsonProperty("thumb")
    var thumb: Optional<PhotoSize>? = null

    @JsonProperty("file_name")
    var fileName: Optional<String>? = null

    @JsonProperty("mime_type")
    var mimeType: Optional<String>? = null

    @JsonProperty("file_size")
    var fileSize: OptionalInt? = null
}