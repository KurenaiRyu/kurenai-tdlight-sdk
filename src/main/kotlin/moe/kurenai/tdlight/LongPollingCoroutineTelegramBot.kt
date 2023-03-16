package moe.kurenai.tdlight

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.coroutines.*
import kotlinx.coroutines.CancellationException
import moe.kurenai.tdlight.client.TDLightCoroutineClient
import moe.kurenai.tdlight.model.message.Update
import moe.kurenai.tdlight.model.message.User
import moe.kurenai.tdlight.request.chat.GetUpdates
import moe.kurenai.tdlight.util.CoroutineUtil.childScopeContext
import moe.kurenai.tdlight.util.getLogger
import java.time.Duration
import java.util.concurrent.*
import java.util.concurrent.Flow.Subscriber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class LongPollingCoroutineTelegramBot(
    private val subscribers: List<Subscriber<Update>>,
    override val client: TDLightCoroutineClient,
    private val publisher: SubmissionPublisher<Update> = SubmissionPublisher<Update>(
        defaultPublishPool(),
        Flow.defaultBufferSize()
    ),
    private val longPollingTimeout: Duration = Duration.ofSeconds(60),
    private var defaultOffset: Long? = -1,
) : TelegramCoroutineBot {

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

    override var coroutineContext: CoroutineContext = CoroutineName("LongPollingCoroutineTelegramBot")
        .plus(CoroutineExceptionHandler { context, e ->
            log.error(context[CoroutineName]?.let { "Exception in coroutine '${it.name}'." }
                ?: "Exception in unnamed coroutine.", e)
        })
        .childScopeContext(EmptyCoroutineContext)
        .apply {
            job.invokeOnCompletion {
                kotlin.runCatching {
                    client.close()
                }.onFailure {
                    if (it !is CancellationException) log.error(it.message, it)
                }
            }
        }

    var status = true
    lateinit var me: User

    init {
        client.coroutineContext = client.coroutineContext.childScopeContext(this.coroutineContext)
    }

    suspend fun start() {
        subscribers.forEach { subscriber: Subscriber<Update> -> publisher.subscribe(subscriber) }
        subscribeToUpdate()

        me = client.getMe()
        log.info("Login by ${me.firstName}${me.lastName?.let { " $it" } ?: ""} @${me.username}")
    }

    override suspend fun subscribeToUpdate() {
        val lastUpdate = atomic(0L)
        launch(coroutineContext) {
            while (isActive) {
                try {
                    val request: GetUpdates = if (lastUpdate.value > 0) {
                        GetUpdates(
                            lastUpdate.value, null, longPollingTimeout.toSeconds().toInt(), null
                        )
                    } else {
                        GetUpdates(defaultOffset, null, longPollingTimeout.toSeconds().toInt(), null).also {
                            defaultOffset = null
                        }
                    }
                    val updates =
                        client.send(request, longPollingTimeout.plus(Duration.ofSeconds(10)), client.updateBaseUrl)
                    updates.forEach(publisher::submit)
                    lastUpdate.update {
                        updates.lastOrNull()?.let { update ->
                            update.updateId + 1
                        } ?: 0L
                    }
                } catch (e: Exception) {
                    delay(Duration.ofSeconds(5).toMillis())
                    log.error("Error on receive update: ${e.message}", e)
                }
            }
        }
    }
}