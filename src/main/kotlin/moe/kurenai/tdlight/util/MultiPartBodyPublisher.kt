package moe.kurenai.tdlight.util

import moe.kurenai.tdlight.util.MultiPartBodyPublisher.PartsSpecification.TYPE
import java.io.IOException
import java.io.InputStream
import java.io.UncheckedIOException
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.function.Supplier

/**
 * MultiPartBodyPublisher
 *
 * copy from https://stackoverflow.com/questions/46392160/java-9-httpclient-send-a-multipart-form-data-request
 *
 */
class MultiPartBodyPublisher {
    private val partsSpecificationList: MutableList<PartsSpecification> = ArrayList()
    val boundary = UUID.randomUUID().toString()
    fun build(): BodyPublisher {
        check(partsSpecificationList.size != 0) { "Must have at least one part to build multipart message." }
        addFinalBoundaryPart()
        return HttpRequest.BodyPublishers.ofByteArrays(Iterable { PartsIterator() })
    }

    fun getByteArras(): Iterable<ByteArray> {
        return Iterable { PartsIterator() }
    }

    fun addPart(name: String?, value: String?): MultiPartBodyPublisher {
        val newPart = PartsSpecification()
        newPart.type = TYPE.STRING
        newPart.name = name
        newPart.value = value
        partsSpecificationList.add(newPart)
        return this
    }

    fun addFile(name: String?, value: Path?): MultiPartBodyPublisher {
        val newPart = PartsSpecification()
        newPart.type = TYPE.FILE
        newPart.name = name
        newPart.path = value
        partsSpecificationList.add(newPart)
        return this
    }

    fun addFile(name: String?, value: Supplier<InputStream>?, filename: String?, contentType: String?): MultiPartBodyPublisher {
        val newPart = PartsSpecification()
        newPart.type = TYPE.STREAM
        newPart.name = name
        newPart.stream = value
        newPart.filename = filename
        newPart.contentType = contentType
        partsSpecificationList.add(newPart)
        return this
    }

    private fun addFinalBoundaryPart() {
        val newPart = PartsSpecification()
        newPart.type = TYPE.FINAL_BOUNDARY
        newPart.value = "--$boundary--"
        partsSpecificationList.add(newPart)
    }

    internal class PartsSpecification {
        enum class TYPE {
            STRING, FILE, STREAM, FINAL_BOUNDARY
        }

        var type: TYPE? = null
        var name: String? = null
        var value: String? = null
        var path: Path? = null
        var stream: Supplier<InputStream>? = null
        var filename: String? = null
        var contentType: String? = null
    }

    internal inner class PartsIterator : Iterator<ByteArray> {

        private val iter: Iterator<PartsSpecification>
        private var currentFileInput: InputStream? = null
        private var done = false
        private var next: ByteArray? = null

        init {
            iter = partsSpecificationList.iterator()
        }

        override fun hasNext(): Boolean {
            if (done) return false
            if (next != null) return true
            next = try {
                computeNext()
            } catch (e: IOException) {
                throw UncheckedIOException(e)
            }
            if (next == null) {
                done = true
                return false
            }
            return true
        }

        override fun next(): ByteArray {
            if (!hasNext()) throw NoSuchElementException()
            val res = next!!
            next = null
            return res
        }

        @Throws(IOException::class)
        private fun computeNext(): ByteArray? {
            return if (currentFileInput == null) {
                if (!iter.hasNext()) return null
                val nextPart = iter.next()
                if (TYPE.STRING == nextPart.type) {
                    val part = "--$boundary\r\n" +
                            "Content-Disposition: form-data; name=\"${nextPart.name}\"\r\n" +
                            "Content-Type: text/plain; charset=UTF-8\r\n\r\n" +
                            "${nextPart.value}\r\n"
                    return part.toByteArray(StandardCharsets.UTF_8)
                }
                if (TYPE.FINAL_BOUNDARY == nextPart.type) {
                    return nextPart.value!!.toByteArray(StandardCharsets.UTF_8)
                }
                val filename: String?
                var contentType: String?
                if (TYPE.FILE == nextPart.type) {
                    val path = nextPart.path
                    filename = path!!.fileName.toString()
                    contentType = Files.probeContentType(path)
                    if (contentType == null) contentType = "application/octet-stream"
                    currentFileInput = Files.newInputStream(path)
                } else {
                    filename = nextPart.filename
                    contentType = nextPart.contentType
                    if (contentType == null) contentType = "application/octet-stream"
                    currentFileInput = nextPart.stream!!.get()
                }
                val partHeader = "--$boundary\r\n" +
                        "Content-Disposition: form-data; name=\"${nextPart.name}\"; filename=\"$filename\"\r\n" +
                        "Content-Type: $contentType\r\n\r\n"
                partHeader.toByteArray(StandardCharsets.UTF_8)
            } else {
                val buf = ByteArray(8192)
                val r = currentFileInput!!.read(buf)
                if (r > 0) {
                    val actualBytes = ByteArray(r)
                    System.arraycopy(buf, 0, actualBytes, 0, r)
                    actualBytes
                } else {
                    currentFileInput!!.close()
                    currentFileInput = null
                    "\r\n".toByteArray(StandardCharsets.UTF_8)
                }
            }
        }
    }
}