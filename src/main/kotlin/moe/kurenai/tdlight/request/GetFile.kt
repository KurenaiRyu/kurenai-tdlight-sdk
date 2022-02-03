package moe.kurenai.tdlight.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.media.File

class GetFile(
    val fileId: String
): Request<ResponseWrapper<File>>(){

    @JsonIgnore
    override val method = "getFile"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<File>>(){}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST



}