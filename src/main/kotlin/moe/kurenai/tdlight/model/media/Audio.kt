package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import java.util.OptionalInt
import moe.kurenai.tdlight.model.media.PhotoSize

data class Audio(

    @JsonProperty("file_id")
    val file_id: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("duration")
    val duration: Long = 0,

    @JsonProperty("performer")
    val performer: String? = null,

    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("file_name")
    val fileName: String? = null,

    @JsonProperty("mime_type")
    val mimeType: String? = null,

    @JsonProperty("file_size")
    val fileSize: Int? = null,

    @JsonProperty("thumb")
    val thumb: PhotoSize? = null,
) {
}