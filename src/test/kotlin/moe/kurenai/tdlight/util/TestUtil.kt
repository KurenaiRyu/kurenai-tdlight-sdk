package moe.kurenai.tdlight.util

import moe.kurenai.tdlight.client.TDLightClient

/**
 * @author Kurenai
 * @since 2023/5/12 7:18
 */

object TestUtil {

    val client: TDLightClient by lazy { TDLightClient(token = getProp("telegram.token")) }

}