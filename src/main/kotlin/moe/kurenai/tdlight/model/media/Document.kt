package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
class Document(

    @JsonProperty("file_id")
    var fileId: String,

    @JsonProperty("file_unique_id")
    var fileUniqueId: String,
) {

    @JsonProperty("thumb")
    var thumb: PhotoSize? = null

    @JsonProperty("file_name")
    var fileName: String? = null

    @JsonProperty("mime_type")
    var mimeType: String? = null

    @JsonProperty("file_size")
    var fileSize: Int? = null
}