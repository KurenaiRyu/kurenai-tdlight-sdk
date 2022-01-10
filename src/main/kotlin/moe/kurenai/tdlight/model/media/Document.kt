package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt
import moe.kurenai.tdlight.model.chat.ChatMember

class Document {
    @JsonProperty("file_id")
    var file_id: String? = null

    @JsonProperty("file_unique_id")
    var fileUniqueId: String? = null

    @JsonProperty("thumb")
    var thumb: Optional<PhotoSize>? = null

    @JsonProperty("file_name")
    var fileName: Optional<String>? = null

    @JsonProperty("mime_type")
    var mimeType: Optional<String>? = null

    @JsonProperty("file_size")
    var fileSize: OptionalInt? = null
}