package moe.kurenai.tdlight.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.User

class GetMe: Request<ResponseWrapper<User>>() {

    @JsonIgnore
    override val method = "getMe"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<User>>(){}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}