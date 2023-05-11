package moe.kurenai.tdlight.request.chat

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.model.message.UpdateType
import moe.kurenai.tdlight.request.Request

data class GetUpdates(
    @JsonProperty("offset") val offset: Long? = null,
    @JsonProperty("limit") val limit: Int? = null,
    @JsonProperty("timeout") val timeout: Int? = null,
    @JsonProperty("allowed_updates") val allowedUpdates: List<UpdateType>? = null
) : Request<ResponseWrapper<List<Update>>>() {

    @JsonIgnore
    override val method = "getUpdates"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<List<Update>>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}