package moe.kurenai.tdlight.request.chat

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import moe.kurenai.tdlight.client.HttpMethod
import moe.kurenai.tdlight.model.ResponseWrapper
import moe.kurenai.tdlight.model.chat.ChatMember
import moe.kurenai.tdlight.request.Request

class GetChatMember(
    @JsonProperty("chat_id") val chatId: String,
    @JsonProperty("user_id") val userId: String,
): Request<ResponseWrapper<ChatMember>>() {

    @JsonIgnore
    override val method = "getChatMember"
    @JsonIgnore
    override val responseType = object : TypeReference<ResponseWrapper<ChatMember>>() {}
    @JsonIgnore
    override val httpMethod = HttpMethod.POST
}