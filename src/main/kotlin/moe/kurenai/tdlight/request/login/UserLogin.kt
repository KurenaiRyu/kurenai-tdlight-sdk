package moe.kurenai.tdlight.request.login

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.login.AuthorizationState
import moe.kurenai.tdlight.request.Request

data class UserLogin(
    val phoneNumber: String
) : Request<ResponseWrapper<AuthorizationState>>() {

    @JsonIgnore
    override val method = "userLogin"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<AuthorizationState>>() {}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST
    @JsonIgnore
    override val needToken = false
}
