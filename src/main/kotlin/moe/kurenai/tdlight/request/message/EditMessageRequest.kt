package moe.kurenai.tdlight.request.message

interface EditMessageRequest {

    val chatId: String?
    val messageId: Long?
    val inlineMessageId: String?

}