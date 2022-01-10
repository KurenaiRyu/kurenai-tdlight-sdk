package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.OptionalInt
import java.util.Optional
import moe.kurenai.tdlight.model.media.PhotoSize

class UserProfilePhotos {
    @JsonProperty("total_count")
    val totalCount: Int? = null

    @JsonProperty("photos")
    val photos: List<List<PhotoSize>>? = null
}