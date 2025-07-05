package ru.netology

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

data class Repost(
    val repostCount: Int = 0,
    val userReposted: Boolean = false
)

data class Post(
    val postId: Int = 0,
    val authorId: Int,
    val authorName: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    val date: Long,
    val replyOwnerId: Int = 0,
    val replies: Int,
    val canPost: Boolean,
    val reposts: Repost
)

object WallService {
    var posts = emptyArray<Post>()
    private var nextId = 1

    fun add(post: Post): Post {
        posts += post.copy(postId = nextId++)
        return posts.last()

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

    fun commentedById(id: Int) {

        for ((index, post) in posts.withIndex()) {
            if (post.postId == id) {
                posts[index] = post.copy(comments = post.comments + 1)
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
    val post = Post(
        authorId = 1,
        authorName = "author",
        content = "content",
        likes = 0,
        comments = 0,
        date = System.currentTimeMillis(),
        replies = 0,
        canPost = true,
        reposts = Repost(1, true)
    )

    val liked = post.copy(likes = post.likes + 1)
    val commented = post.copy(comments = post.comments + 1)
    println(liked)
    WallService.publicationTime(post.date)

    val (postId, authorId, _, content) = post
    println("$postId, $authorId, $content")

}