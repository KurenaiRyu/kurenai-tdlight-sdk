package moe.kurenai.tdlight.model.keyboard

import com.fasterxml.jackson.annotation.JsonProperty
import moe.kurenai.tdlight.annotation.NoArg
import moe.kurenai.tdlight.model.message.LoginUrl

@NoArg
data class InlineKeyboardButton(

    @JsonProperty("text")
    val text: String,
) {

    @JsonProperty("url")
    var url: String? = null

    @JsonProperty("login_url")
    var loginUrl: LoginUrl? = null

    @JsonProperty("callback_data")
    var callbackData: String? = null

    @JsonProperty("switch_inline_query")
    var switchInlineQuery: String? = null

    @JsonProperty("switch_inline_query_current_chat")
    var switchInlineQueryCurrentChat: String? = null

    @JsonProperty("pay")
    var pay: Boolean? = null
}