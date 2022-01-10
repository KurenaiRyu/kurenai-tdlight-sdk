package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.OptionalLong
import java.util.Optional

data class File(

    @JsonProperty("file_id")
    val fileId: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,

    @JsonProperty("file_size")
    val fileSize: Long? = null,

    @JsonProperty("file_path")
    val filePath: String? = null,
) {
}