package moe.kurenai.tdlight.util

object MarkdownUtil {

    private val formatChar = "_*[]()~`>#+-=|{}.!".toCharArray()

    fun String.fm2md(): String = MarkdownUtil.format(this)

    private fun format(target: String): String {
        var result = target
        for (c in formatChar) {
            result = result.replace(c.toString(), "\\$c")
        }
        return result
    }
}