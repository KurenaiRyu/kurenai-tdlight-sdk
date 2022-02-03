package moe.kurenai.tdlight.request.message

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.request.Request

class AnswerCallbackQuery(
    val callbackQueryId: String,
): Request<ResponseWrapper<Boolean>>() {

    var text: String? = null
    var showAlert: Boolean = false
    var url: String? = null
    var cacheTime: Int? = null

    @JsonIgnore
    override val method = "answerCallbackQuery"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Boolean>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}