package moe.kurenai.tdlight.request.message

interface Reply {
    var replyToMessageId: Long?
    var allowSendingWithoutReply: Boolean?
}