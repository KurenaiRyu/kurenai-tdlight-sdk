package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.UserClient

interface TelegramBot {

    val client: UserClient

    fun subscribeToUpdate()
}