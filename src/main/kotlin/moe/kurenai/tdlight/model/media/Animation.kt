package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class Animation(

    @JsonProperty("file_id")
    val fileId: String,

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