package moe.kurenai.tdlight.model.inline

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import moe.kurenai.tdlight.annotation.NoArg

@NoArg
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    value = [
        JsonSubTypes.Type(value = InlineQueryResultPhoto::class, name = "photo"),
        JsonSubTypes.Type(value = InlineQueryResultArticle::class, name = "article"),
    ]
)
abstract class InlineQueryResult(
    val type: String,
    val id: String,
) {
}