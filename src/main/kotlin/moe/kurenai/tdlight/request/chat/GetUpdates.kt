package moe.kurenai.tdlight.request.chat

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.model.message.UpdateTypes
import moe.kurenai.tdlight.request.Request

class GetUpdates(
    @JsonProperty("offset") val offset: Long?,
    @JsonProperty("limit") val limit: Int?,
    @JsonProperty("timeout") val timeout: Int?,
    @JsonProperty("allowed_updates") val allowedUpdates: List<UpdateTypes>?
): Request<ResponseWrapper<List<Update>>>() {

    @JsonIgnore
    override val method = "getUpdates"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<List<Update>>>() {}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}