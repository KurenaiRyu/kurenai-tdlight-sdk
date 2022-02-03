package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import java.util.*

@NoArg
class VideoNote {
    @JsonProperty("file_id")
    var fileId: String? = null

    @JsonProperty("file_unique_id")
    var fileUniqueId: String? = null

    @JsonProperty("length")
    var length = 0

    @JsonProperty("duration")
    var duration = 0

    @JsonProperty("thumb")
    var thumb: PhotoSize? = null

    @JsonProperty("file_size")
    var fileSize: OptionalInt? = null
}