package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.inline.InlineQueryResult
import moe.kurenai.tdlight.request.Request
import moe.kurenai.tdlight.util.DefaultMapper

@NoArg
class AnswerInlineQuery(
    val inlineQueryId: String,
) : Request<ResponseWrapper<Boolean>>() {

    @JsonIgnore
    var inlineResults: List<InlineQueryResult> = emptyList()

    var isPersonal: Boolean = false
    var nextOffset: String? = null
    var switchPmText: String? = null
    var switchPmParameter: String? = null
    var cacheTime: Int? = null

    val results: String
        get() = DefaultMapper.MAPPER.writeValueAsString(inlineResults)

    @JsonIgnore
    override val method = "answerInlineQuery"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Boolean>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}