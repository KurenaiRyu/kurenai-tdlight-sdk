package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.UserClient
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.model.message.UpdateTypes
import moe.kurenai.tdlight.request.chat.GetUpdates
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.Flow.Subscriber
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.SubmissionPublisher
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Consumer

class LongPollingTelegramBot : TelegramBot {
    private val executorService: ScheduledExecutorService
    private var initialDelay = 1
    private var period = 1
    override val client: UserClient
    private val publisher = SubmissionPublisher<Update>()
    var status = true

    constructor(userClient: UserClient, subscribers: List<Subscriber<Update>?>) {
        this.client = userClient
        executorService = Executors.newSingleThreadScheduledExecutor()
        subscribers.forEach(Consumer { subscriber: Subscriber<Update>? -> publisher.subscribe(subscriber) })
    }

    constructor(
        host: String?, token: String?, executorService: ScheduledExecutorService,
        subscriber: Subscriber<Update>?
    ) {
        client = UserClient(host!!, true)
        this.executorService = executorService
        publisher.subscribe(subscriber)
    }

    constructor(host: String?, token: String?, subscriber: Subscriber<Update>?) {
        client = UserClient(host!!, true)
        executorService = Executors.newSingleThreadScheduledExecutor()
        publisher.subscribe(subscriber)
    }

    override fun subscribeToUpdate() {
        val lastUpdate = AtomicLong()
        executorService.scheduleAtFixedRate(
            {
                try {
                    val request: GetUpdates = if (lastUpdate.get() > 0) {
                        GetUpdates(
                            lastUpdate.get(),
                            null, null,
                            java.util.List.of(UpdateTypes.MESSAGE, UpdateTypes.CALLBACK_QUERY, UpdateTypes.CHANNEL_POST)
                        )
                    } else {
                        GetUpdates(null, null, null, null)
                    }
                    val updates = client.sendSync(request).result
                    updates?.forEach(publisher::submit)
                    handleLastUpdateId(updates?.lastOrNull(), lastUpdate)
                } catch (e: Exception) {
                    logger.error("Error on receive update", e)
                }
            },
            initialDelay.toLong(),
            period.toLong(),
            TimeUnit.SECONDS
        )
    }

    private fun handleLastUpdateId(update: Update?, lastUpdate: AtomicLong) {
        update?.let { lastUpdate.set(update.updateId().orElse(0) + 1) } ?: lastUpdate.set(0)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(LongPollingTelegramBot::class.java)
    }
}