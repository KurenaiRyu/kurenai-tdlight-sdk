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

class PassportFile {
    @JsonProperty("file_id")
    var fileId: String? = null

    @JsonProperty("file_unique_id")
    var fileUniqueId: String? = null

    @JsonProperty("file_size")
    var fileSize = 0

    @JsonProperty("file_date")
    var fileDate = 0
}