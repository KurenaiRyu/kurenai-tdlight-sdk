package moe.kurenai.tdlight.util

import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * @author Kurenai
 * @since 9/5/2022 00:16:03
 */

object CoroutineUtil {

    public fun CoroutineContext.childScopeContext(
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CoroutineContext {
        val ctx = this + coroutineContext
        val job = ctx[Job] ?: return ctx + SupervisorJob()
        return ctx + SupervisorJob(job)
    }

}