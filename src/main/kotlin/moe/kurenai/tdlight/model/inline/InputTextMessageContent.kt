package moe.kurenai.tdlight.model.inline

data class InputTextMessageContent(
    var messageText: String
) : InputMessageContent() {
    var disableWebPagePreview: Boolean = false
}