package ru.netology

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class PostNotFoundException(message: String) : RuntimeException(message)
class Comment(
    val authorId: Int,
    val comment: String
)
data class Post(
    val postId: Int = 0,
    val authorId: Int,
    val authorName: String,
    val content: String,
    var likes: Int,
    val date: Long,
    val replyOwnerId: Int?,
    val replies: Int = 0,
    val original: Post?,
    val canPost: Boolean,
    val attachments: Array<Attachments> = emptyArray()
) {

}

object WallService {
    var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextId = 1

    fun add(post: Post): Post {
        posts += post.copy(postId = nextId++)
        return posts.last()

    }

    fun createComment( searchPostId: Int, comment: Comment): Comment {
        for (post in posts) {
            if(post.postId == searchPostId){
                comments += comment
                return comment
            }
        }
        throw PostNotFoundException("No post with $searchPostId")
    }

    fun addRepost(repost: Post) {

        val newRepost = repost.copy(
            postId = nextId++,
            likes = 0,
            replies = 0,
            replyOwnerId = null
        )

        posts += newRepost
    }


    fun publicationTime(published: Long) {
        val instant = Instant.ofEpochMilli(published)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())

        val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            .withLocale(Locale.getDefault())

        println("Дата публикации: ${zonedDateTime.format(dateFormatter)}")
    }


    fun likeById(id: Int) {

        for ((index, post) in posts.withIndex()) {
            if (post.postId == id) {
                posts[index] = post.copy(likes = post.likes + 1)
            }
        }
    }


    fun repliedById(id: Int) {
        for ((index, post) in posts.withIndex()) {
            if (post.postId == id) {
                posts[index] = post.copy(replies = post.replies + 1)
            }
        }
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.postId == newPost.postId) {
                posts[index] = newPost
                return true
            }
        }
        return false
    }


    fun clear() {
        posts = emptyArray()
        nextId = 1
    }
}


fun main() {
    val photo = Photo(
        id = 100,
        authorId = 15,
        authorName = "nobodyKnows",
        comments = 100,
        likes = 1500
    )

    val audio = Audio(
        id = 146,
        authorId = 1434,
        authorName = "it",
        duration = 2,
        comments = 3,
        likes = 15
    )

    val doc = Doc(
        id = 3465,
        authorId = 1242,
        authorName = "anotherAuthor"
    )
    val link = Link(
        url = "https://images.squarespace-cdn.com/content/v1/62da63f9ec4d5d07d12a1056/dbcc69f8-1a3c-4dfc-bf84-7ce128c566ac/20210823143248_IMG_4007a.jpg"
    )

    val video = Video(
        id = 100,
        authorId = 15,
        authorName = "nobodyKnows",
        comments = 100,
        duration = 3,
        likes = 1500
    )

    val photoAttachment = PhotoAttachment("photo", photo)
    val audioAttachment = AudioAttachment("audio", audio)

    val post = Post(
        postId = 1,
        authorId = 1,
        authorName = "me",
        content = "original content",
        likes = 15,
        date = System.currentTimeMillis(),
        replyOwnerId = null,
        replies = 0,
        original = null,
        canPost = true,
        attachments = arrayOf(photoAttachment, audioAttachment)
    )
    val repost = Post(
        postId = 2,
        authorId = 2,
        authorName = "he",
        content = "original content",
        likes = 5,
        date = System.currentTimeMillis(),
        replyOwnerId = null,
        replies = 0,
        original = post,
        canPost = true,
        attachments = post.attachments
    )
    val array = arrayOf(post, repost)
    var likesSum = 0
    for (item in array) {
        likesSum += (item.likes + (item.original?.likes ?: 0))
    }
    println(likesSum)

    val replArray = arrayOf(post, repost)
    var replSum = 0
    for (item in replArray) {
        replSum += (item.replies + (item.original?.replies ?: 0))
    }
    println(replSum)
    WallService.add(post)
    WallService.add(repost)
    val comment = Comment(2, "cool")

    WallService.createComment(2,comment)
}