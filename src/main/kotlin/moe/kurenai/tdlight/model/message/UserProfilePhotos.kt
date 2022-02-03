package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.media.PhotoSize

@NoArg
class UserProfilePhotos {
    @JsonProperty("total_count")
    val totalCount: Int? = null

    @JsonProperty("photos")
    val photos: List<List<PhotoSize>>? = null
}