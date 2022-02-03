package moe.kurenai.tdlight.model.media

import java.io.File
import java.io.InputStream
import java.nio.file.Files

class InputFile {

    lateinit var attachName: String
    var fileName: String? = null
    var mimeType: String? = null
    var inputStream: InputStream? = null
    var file: File? = null
    var isNew: Boolean = false

    constructor()

    constructor(attachName: String) {
        this.attachName = attachName
    }

    constructor(file: File, mimeType: String? = null) {
        this.attachName = "attach://${file.name}"
        this.file = file
        this.fileName = file.name
        this.mimeType = mimeType ?: Files.probeContentType(file.toPath())
        this.isNew = true
    }

    constructor(inputStream: InputStream, fileName: String, mimeType: String? = null) {
        this.attachName = "attach://${fileName}"
        this.inputStream = inputStream
        this.fileName = fileName
        this.mimeType = mimeType
        this.file = null
        this.isNew = true
    }

    companion object {
        fun File.toInputFile(): InputFile {
            return InputFile(this)
        }
    }
}