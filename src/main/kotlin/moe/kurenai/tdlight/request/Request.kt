package moe.kurenai.tdlight.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod

abstract class Request<T> {

    abstract val method: String
    abstract val responseType: TypeReference<T>
    abstract val httpMethod: HttpMethod
    @JsonIgnore
    open val needToken: Boolean = true

}