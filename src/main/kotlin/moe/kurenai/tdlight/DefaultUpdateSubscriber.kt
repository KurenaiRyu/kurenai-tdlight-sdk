package moe.kurenai.tdlight

import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.util.DefaultMapper.convertToString
import org.apache.logging.log4j.LogManager

class DefaultUpdateSubscriber : AbstractUpdateSubscriber() {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun onSubscribe0() {
        log.info("New subscription to UpdateSubscriber ")
    }

    override fun onNext0(update: Update) {
        log.debug("Received {}", convertToString(update))
    }

    override fun onError0(e: Throwable) {
        log.error("Error on processing update ", e)
    }

    override fun onComplete0() {
        log.info("Complete processing")
    }
}