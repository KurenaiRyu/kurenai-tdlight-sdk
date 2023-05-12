package moe.kurenai.tdlight.request.login

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.message.User
import moe.kurenai.tdlight.request.Request

class LogOut : Request<ResponseWrapper<Boolean>>() {

    @JsonIgnore
    override val method = "logOut"

    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<Boolean>>() {}

    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}