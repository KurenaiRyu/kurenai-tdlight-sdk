package moe.kurenai.tdlight.model.message

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.model.chat.ChatJoinRequest
import moe.kurenai.tdlight.model.chat.ChatMemberUpdated
import moe.kurenai.tdlight.model.media.PreCheckoutQuery
import moe.kurenai.tdlight.model.media.ShippingQuery
import moe.kurenai.tdlight.model.poll.Poll
import moe.kurenai.tdlight.model.poll.PollAnswer

data class Update(
    @JsonProperty("update_id") val updateId: Long,
    @JsonProperty("message") val message: Message?,
    @JsonProperty("edited_message") val editedMessage: Message?,
    @JsonProperty("channel_post") val channelPost: Message?,
    @JsonProperty("edited_channel_post") val editedChannelPost: Message?,
    @JsonProperty("inline_query") val inlineQuery: InlineQuery?,
    @JsonProperty("chosen_inline_result") val chosenInlineResult: ChosenInlineResult?,
    @JsonProperty("callback_query") val callbackQuery: CallbackQuery?,
    @JsonProperty("shipping_query") val shippingQuery: ShippingQuery?,
    @JsonProperty("pre_checkout_query") val preCheckoutQuery: PreCheckoutQuery?,
    @JsonProperty("poll") val poll: Poll?,
    @JsonProperty("poll_answer") val pollAnswer: PollAnswer?,
    @JsonProperty("my_chat_member") val myChatMember: ChatMemberUpdated?,
    @JsonProperty("chat_member") val chatMember: ChatMemberUpdated?,
    @JsonProperty("chat_join_request") val chatJoinRequest: ChatJoinRequest?
) {

    fun hasCallbackQuery() = callbackQuery != null
    fun hasEditedMessage() = editedMessage != null
    fun hasMessage() = message != null

    fun hasInlineQuery(): Boolean {
        return inlineQuery != null
    }

    fun hasChosenInlineResult(): Boolean {
        return chosenInlineResult != null
    }

    fun hasChannelPost(): Boolean {
        return channelPost != null
    }

    fun hasEditedChannelPost(): Boolean {
        return editedChannelPost != null
    }

    fun hasShippingQuery(): Boolean {
        return shippingQuery != null
    }

    fun hasPreCheckoutQuery(): Boolean {
        return preCheckoutQuery != null
    }

    fun hasPoll(): Boolean {
        return poll != null
    }

    fun hasPollAnswer(): Boolean {
        return pollAnswer != null
    }

    fun hasMyChatMember(): Boolean {
        return myChatMember != null
    }

    fun hasChatMember(): Boolean {
        return chatMember != null
    }

    fun hasChatJoinRequest(): Boolean {
        return chatJoinRequest != null
    }
}