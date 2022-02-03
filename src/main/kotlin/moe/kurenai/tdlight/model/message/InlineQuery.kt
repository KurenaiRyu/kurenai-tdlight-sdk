package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.chat.ChatType
import moe.kurenai.tdlight.model.media.Location

@NoArg
data class InlineQuery(
    @JsonProperty("id")
    var id: String,

    @JsonProperty("from")
    var from: User,

    @JsonProperty("query")
    var query: String,

    @JsonProperty("offset")
    var offset: String,

    @JsonProperty("chat_type")
    var chatType: ChatType? = null,

    @JsonProperty("location")
    var location: Location? = null,
)