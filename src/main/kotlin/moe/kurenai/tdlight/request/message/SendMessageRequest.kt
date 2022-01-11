package moe.kurenai.tdlight.request.message

interface SendMessageRequest {

    val chatId: String
    val disableNotification: Boolean?
    val sendAt: Long?

}