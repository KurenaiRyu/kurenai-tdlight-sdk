package moe.kurenai.tdlight

import moe.kurenai.tdlight.model.message.Update
import org.apache.logging.log4j.LogManager
import java.util.concurrent.Flow
import java.util.concurrent.Flow.Subscriber

abstract class AbstractUpdateSubscriber : Subscriber<Update> {

    companion object {
        private val log = LogManager.getLogger()
    }

    private var subscription: Flow.Subscription? = null
    override fun onSubscribe(subscription: Flow.Subscription) {
        this.subscription = subscription
        this.subscription!!.request(1)
        onSubscribe0()
    }

    abstract fun onSubscribe0()

    override fun onNext(update: Update) {
        onNext0(update)
        subscription!!.request(1)
    }

    abstract fun onNext0(update: Update)

    override fun onError(e: Throwable) {
        onError0(e)
    }

    abstract fun onError0(e: Throwable)

    override fun onComplete() {
        onComplete0()
    }

    abstract fun onComplete0()
}