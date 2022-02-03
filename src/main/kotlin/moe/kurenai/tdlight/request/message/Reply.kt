package moe.kurenai.tdlight.request.message

interface Reply {
    var replyToMessageId: Int?
    var allowSendingWithoutReply: Boolean?
}