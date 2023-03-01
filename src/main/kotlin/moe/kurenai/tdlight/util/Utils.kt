package moe.kurenai.tdlight.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Kurenai
 * @since 2023/1/27 20:28
 */


fun getLogger(name: String = Thread.currentThread().stackTrace[2].className): Logger {
    return LoggerFactory.getLogger(name)
}