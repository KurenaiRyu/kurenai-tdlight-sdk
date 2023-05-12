package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.TDLightClient
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.request.chat.GetUpdates
import moe.kurenai.tdlight.util.getLogger
import java.time.Duration
import java.util.concurrent.*
import java.util.concurrent.Flow.Subscriber
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Consumer

class LongPollingTelegramBot : TelegramBot {

    companion object {
        private val log = getLogger()

        private fun defaultPublishPool(): ThreadPoolExecutor {
            return ThreadPoolExecutor(
                1, 1, 1L, TimeUnit.MILLISECONDS,
                LinkedBlockingQueue(300)
            ) { r ->
                val t = Thread(
                    Thread.currentThread().threadGroup, r,
                    "telegram-publish",
                    0
                )
                if (t.isDaemon) t.isDaemon = false
                if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                t
            }
        }
    }

    private val executorService: ScheduledExecutorService
    private val publisher: SubmissionPublisher<Update>
    private var defaultOffset: Long = -1
    override val client: TDLightClient
    val longPollingTimeout: Duration
    var status = true

    constructor(
        subscribers: List<Subscriber<Update>>,
        client: TDLightClient,
        longPollingTimeout: Duration = Duration.ofSeconds(60),
        defaultOffset: Long = -1,
    ) {
        this.client = client
        this.longPollingTimeout = longPollingTimeout
        this.defaultOffset = defaultOffset
        this.executorService = Executors.newSingleThreadScheduledExecutor()
        this.publisher = SubmissionPublisher<Update>(defaultPublishPool(), Flow.defaultBufferSize())
        subscribers.forEach { subscriber: Subscriber<Update> -> publisher.subscribe(subscriber) }
        subscribeToUpdate()
    }

    constructor(
        subscribers: List<Subscriber<Update>>,
        client: TDLightClient,
        executorService: ScheduledExecutorService,
        publish: SubmissionPublisher<Update>,
        longPollingTimeout: Duration = Duration.ofSeconds(60),
        defaultOffset: Long = -1,
    ) {
        this.client = client
        this.longPollingTimeout = longPollingTimeout
        this.defaultOffset = defaultOffset
        this.executorService = executorService
        this.publisher = publish
        subscribers.forEach(Consumer { subscriber: Subscriber<Update> -> publisher.subscribe(subscriber) })
        subscribeToUpdate()
    }

    override fun subscribeToUpdate() {
        val lastUpdate = AtomicLong()
        executorService.scheduleWithFixedDelay(
            {
                try {
                    val request: GetUpdates = if (lastUpdate.get() > 0) {
                        GetUpdates(
                            lastUpdate.get(), null, longPollingTimeout.toSeconds().toInt(), null
                        )
                    } else {
                        if (0 > defaultOffset) {
                            GetUpdates(defaultOffset, 1, longPollingTimeout.toSeconds().toInt(), null)
                            defaultOffset *= -1
                            GetUpdates(null, null, longPollingTimeout.toSeconds().toInt(), null)
                        } else {
                            GetUpdates(null, null, longPollingTimeout.toSeconds().toInt(), null)
                        }
                    }
                    val updates =
                        client.sendSync(request, longPollingTimeout.plus(Duration.ofSeconds(10)), client.updateBaseUrl)
                    updates.forEach(publisher::submit)
                    handleLastUpdateId(updates.lastOrNull(), lastUpdate)
                } catch (e: Exception) {
                    log.error("Error on receive update", e)
                }
            },
            5L,
            1L,
            TimeUnit.SECONDS
        )
    }

    private fun handleLastUpdateId(update: Update?, lastUpdate: AtomicLong) {
        update?.let { lastUpdate.set(update.updateId + 1) } ?: lastUpdate.set(0)
    }
}