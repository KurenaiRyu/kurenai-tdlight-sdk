package moe.kurenai.tdlight

import moe.kurenai.tdlight.request.login.LogOut
import moe.kurenai.tdlight.util.TestUtil
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author Kurenai
 * @since 2023/5/12 7:17
 */

class LogoutTest {

    @Test
    fun test() {
        val result = TestUtil.client.sendSync(LogOut())
        assertTrue(result)
    }

}