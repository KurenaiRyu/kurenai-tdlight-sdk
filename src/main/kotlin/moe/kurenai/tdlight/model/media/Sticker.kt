package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class Sticker(

    @JsonProperty("file_id")
    val fileId: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("width")
    val width: Int = 0,

    @JsonProperty("height")
    val height: Int = 0,

    @JsonProperty("is_animated")
    val isAnimated: Boolean,

    @JsonProperty("is_video")
    val isVideo: Boolean,

    @JsonProperty("thumb")
    val thumb: PhotoSize? = null,

    @JsonProperty("emoji")
    val emoji: String? = null,

    @JsonProperty("set_name")
    val fileName: String? = null,

    @JsonProperty("mask_position")
    val mimeType: MaskPosition? = null,

    @JsonProperty("file_size")
    val fileSize: Int? = null,
) {
}