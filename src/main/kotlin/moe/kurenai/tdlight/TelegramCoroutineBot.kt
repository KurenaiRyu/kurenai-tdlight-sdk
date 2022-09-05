package moe.kurenai.tdlight

import kotlinx.coroutines.CoroutineScope
import moe.kurenai.tdlight.client.TDLightCoroutineClient

interface TelegramCoroutineBot : CoroutineScope {

    val client: TDLightCoroutineClient

    suspend fun subscribeToUpdate()
}