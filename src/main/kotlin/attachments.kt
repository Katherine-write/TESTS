package ru.netology

import java.net.URL

interface Attachments {
    val type: String
}

data class Video (
    val id: Long,
    val authorId: Long,
    val authorName: String,
    val duration: Int,
    val comments: Int,
    val likes: Int
)

data class Audio (
    val id: Long,
    val authorId: Long,
    val authorName: String,
    val duration: Int,
    val comments: Int,
    val likes: Int
)

data class Photo (
    val id: Long,
    val authorId: Long,
    val authorName: String,
    val comments: Int,
    val likes: Int
)

data class Doc (
    val id: Long,
    val authorId: Long,
    val authorName: String
)

data class Link (
    val url: String
)

class VideoAttachment(
    override val type: String = "video",
    val video: Video
) : Attachments {

}

class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio
) : Attachments {

}
class PhotoAttachment(
    override val type: String = "photo",
    val photo: Photo
) : Attachments {

}

class DocAttachment(
    override val type: String = "doc",
    val doc: Doc
) : Attachments {

}
class LinkAttachment(
    override val type: String = "link",
    val link: Link
) : Attachments {

}
