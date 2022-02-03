package moe.kurenai.tdlight.model.voice

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
data class Voice(

    @JsonProperty("file_id")
    val fileId: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("duration")
    val duration: Long = 0,

    @JsonProperty("mime_type")
    val mimeType: String? = null,

    @JsonProperty("file_size")
    val fileSize: Int? = null,
) {
}