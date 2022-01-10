package moe.kurenai.tdlight.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.login.AuthorizationState
import moe.kurenai.tdlight.request.Request

class Ping : Request<ResponseWrapper<Float>>() {

    @JsonIgnore
    override val method = "ping"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Float>>(){}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}
