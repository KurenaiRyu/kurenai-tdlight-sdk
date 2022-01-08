package moe.kurenai.tdlight

import java.util.concurrent.Flow.Subscriber
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.UpdateSubscriber
import org.slf4j.LoggerFactory
import java.util.concurrent.Flow

class UpdateSubscriber : Subscriber<Update> {
    private var subscription: Flow.Subscription? = null
    override fun onSubscribe(subscription: Flow.Subscription) {
        LOGGER.info("New subscription to UpdateSubscriber ")
        this.subscription = subscription
        this.subscription!!.request(1)
    }

    override fun onNext(item: Update) {
        LOGGER.debug("Received new update with id {}", item.updateId().orElseThrow())
        subscription!!.request(1)
    }

    override fun onError(e: Throwable) {
        LOGGER.error("Error on processing update ", e)
    }

    override fun onComplete() {
        LOGGER.info("Complete processing")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(UpdateSubscriber::class.java)
    }
}