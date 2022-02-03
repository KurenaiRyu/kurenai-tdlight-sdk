package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty

data class File(

    @JsonProperty("file_id")
    val fileId: String,

    @JsonProperty("file_unique_id")
    val fileUniqueId: String,
) {

    @JsonProperty("file_size")
    var fileSize: Long? = null

    @JsonProperty("file_path")
    var filePath: String? = null

    fun getFileUrl(token: String, isUserMode: Boolean = false): String {
        return "https://api.telegram.org/file/${if (isUserMode) "user" else "bot" }${token}/${filePath}"
    }
}