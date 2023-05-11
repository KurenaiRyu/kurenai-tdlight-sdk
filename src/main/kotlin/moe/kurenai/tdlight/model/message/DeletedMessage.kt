package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.Chat
import moe.kurenai.tdlight.model.media.*

data class DeletedMessage(
    @JsonProperty("message_ids") val messageIds: List<Long>,
    @JsonProperty("chat") val chat: Chat,
) {
    val chatId = chat.id.toString()
    fun isUserMessage(): Boolean = chat.isUserChat()
    fun isGroupMessage(): Boolean = chat.isGroupChat()
    fun isSuperGroupMessage(): Boolean = chat.isSupperGroupChat()
    fun isChannelMessage(): Boolean = chat.isChannelChat()
}