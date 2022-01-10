package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize
import java.util.OptionalInt

data class Animation(

    @JsonProperty("file_id")
    val file_id: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("width")
    val width: Int = 0,

    @JsonProperty("height")
    val height: Int = 0,

    @JsonProperty("duration")
    val duration: Long = 0,

    @JsonProperty("thumb")
    val thumb: PhotoSize? = null,

    @JsonProperty("file_name")
    val fileName: String? = null,

    @JsonProperty("mime_type")
    val mimeType: String? = null,

    @JsonProperty("file_size")
    val fileSize: Int? = null,
) {
}