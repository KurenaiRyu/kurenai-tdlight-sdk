package moe.kurenai.tdlight.exception

import moe.kurenai.tdlight.model.ResponseWrapper

class TelegramApiRequestException: TelegramApiException {

    var response: ResponseWrapper<*>? = null

    constructor(message: String, response: ResponseWrapper<*>): super(message) {
        this.response = response
    }
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)

}