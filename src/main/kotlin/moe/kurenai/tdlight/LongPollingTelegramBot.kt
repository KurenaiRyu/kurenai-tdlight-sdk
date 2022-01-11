package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.TDLightClient
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.request.chat.GetUpdates
import org.apache.logging.log4j.LogManager
import java.time.Duration
import java.util.concurrent.*
import java.util.concurrent.Flow.Subscriber
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Consumer

class LongPollingTelegramBot : TelegramBot {

    companion object {
        private val log = LogManager.getLogger()
    }

    private val executorService: ScheduledExecutorService
    private var initialDelay = 1L
    private var period = 1L
    private val publisher: SubmissionPublisher<Update>
    override val client: TDLightClient
    var status = true

    constructor(client: TDLightClient, subscribers: List<Subscriber<Update>>) {
        this.client = client
        this.executorService = Executors.newSingleThreadScheduledExecutor()
        this.publisher = SubmissionPublisher<Update>(defaultPublishPool(), Flow.defaultBufferSize())
        subscribers.forEach(Consumer { subscriber: Subscriber<Update> -> publisher.subscribe(subscriber) })
        subscribeToUpdate()
    }

    constructor(
        client: TDLightClient,
        executorService: ScheduledExecutorService,
        publish: SubmissionPublisher<Update>,
        subscribers: List<Subscriber<Update>>
    ) {
        this.client = client
        this.executorService = executorService
        this.publisher = publish
        subscribers.forEach(Consumer { subscriber: Subscriber<Update> -> publisher.subscribe(subscriber) })
        subscribeToUpdate()
    }

    override fun subscribeToUpdate() {
        val lastUpdate = AtomicLong()
        executorService.scheduleAtFixedRate(
            {
                try {
                    val request: GetUpdates = if (lastUpdate.get() > 0) {
                        GetUpdates(
                            lastUpdate.get(), null, null, null
                        )
                    } else {
                        GetUpdates(null, null, null, null)
                    }
                    client.sendSync(request, Duration.ZERO).result?.let { updates ->
                        updates.forEach(publisher::submit)
                        handleLastUpdateId(updates.lastOrNull(), lastUpdate)
                    }
                } catch (e: Exception) {
                    log.error("Error on receive update", e)
                }
            },
            initialDelay,
            period,
            TimeUnit.SECONDS
        )
    }

    private fun handleLastUpdateId(update: Update?, lastUpdate: AtomicLong) {
        update?.let { lastUpdate.set(update.updateId + 1) } ?: lastUpdate.set(0)
    }

    private fun defaultPublishPool(): ThreadPoolExecutor {
        return ThreadPoolExecutor(
            1, 1, 1L, TimeUnit.MILLISECONDS,
            LinkedBlockingQueue(300), object : ThreadFactory {
                val threadNumber = AtomicInteger(0)
                override fun newThread(r: Runnable): Thread {
                    val t = Thread(
                        Thread.currentThread().threadGroup, r,
                        "telegram-publish-" + threadNumber.getAndIncrement(),
                        0
                    )
                    if (t.isDaemon) t.isDaemon = false
                    if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                    return t
                }
            }
        )
    }
}