package moe.kurenai.tdlight

import moe.kurenai.tdlight.client.TDLightClient
import moe.kurenai.tdlight.model.command.BotCommand
import moe.kurenai.tdlight.request.command.GetMyCommands
import moe.kurenai.tdlight.request.command.SetMyCommands
import moe.kurenai.tdlight.util.TestUtil.client
import moe.kurenai.tdlight.util.getProp
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Kurenai
 * @since 2023/5/12 3:43
 */

class CommandTest {

    @Test
    fun testGetCommand() {
        val result = client.sendSync(GetMyCommands())
        println("Result: {$result}")
    }

    @Test
    fun testSetCommand() {
        val command = "test"
        val description = "Test command description"
        client.sendSync(SetMyCommands(listOf(BotCommand(command, description))))
        val result = client.sendSync(GetMyCommands())
        assertEquals(result.first().command, command)
        assertEquals(result.first().description, description)
        println("Result: {$result}")
    }

}