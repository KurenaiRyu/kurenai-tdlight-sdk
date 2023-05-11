package moe.kurenai.tdlight.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.file.Path
import java.util.*
import kotlin.io.path.inputStream

/**
 * @author Kurenai
 * @since 2023/1/27 20:28
 */


fun getLogger(name: String = Thread.currentThread().stackTrace[2].className): Logger {
    return LoggerFactory.getLogger(name)
}

val localProperties by lazy {
    Properties().also { it.load(Path.of("local.properties").inputStream()) }
}

fun getProp(key: String) = localProperties.getProperty(key) ?: System.getProperty(key) ?: System.getenv(key)