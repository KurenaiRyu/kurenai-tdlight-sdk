package moe.kurenai.tdlight.request.message

import moe.kurenai.tdlight.model.media.InputFile

interface InputMedia {
    val type: String
    val media: InputFile
}