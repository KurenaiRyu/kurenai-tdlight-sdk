package moe.kurenai.tdlight.model.media

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.MessageEntity

@NoArg
class Game {
    @JsonProperty("title")
    var title: String? = null

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("photo")
    var photo: List<PhotoSize>? = null

    @JsonProperty("text")
    var text: String? = null

    @JsonProperty("text_entities")
    var textEntities: List<MessageEntity>? = null

    @JsonProperty("animation")
    var animation: Animation? = null
}