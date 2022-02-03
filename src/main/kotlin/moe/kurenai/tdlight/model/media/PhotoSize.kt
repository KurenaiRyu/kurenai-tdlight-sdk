package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
class PhotoSize(

    @JsonProperty("file_id")
    var fileId: String,

    @JsonProperty("file_unique_id")
    var fileUniqueId: String,
) {

    @JsonProperty("width")
    var width = 0

    @JsonProperty("height")
    var height = 0

    @JsonProperty("file_size")
    var fileSize: Int? = null
}