package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.TDLightClient

interface TelegramBot {

    val client: TDLightClient

    fun subscribeToUpdate()
}