package moe.kurenai.tdlight.model.media

data class FileEntity(
    val fileName: String,
    val mimeType: String,
    val content: ByteArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileEntity

        if (fileName != other.fileName) return false
        if (mimeType != other.mimeType) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = fileName.hashCode()
        result = 31 * result + mimeType.hashCode()
        result = 31 * result + content.contentHashCode()
        return result
    }
}