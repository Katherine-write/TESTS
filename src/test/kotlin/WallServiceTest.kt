import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.Comment
import ru.netology.Post
import ru.netology.PostNotFoundException
import ru.netology.WallService
import ru.netology.WallService.createComment
import ru.netology.WallService.posts


class WallServiceTest {

    @Test
    fun add() {
        val testPost = Post(
            authorId = 1,
            authorName = "testUser",
            content = "testContent",
            likes = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
            original = null,
            canPost = true
        )

        val result = WallService.add(testPost)

        assertEquals(1, result.postId)
    }

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun updateExisting() {
        val testPost = Post(
            authorId = 1,
            authorName = "testUser",
            content = "testContent",
            likes = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
            original = null,
            canPost = true
        )

        val newTestPost = WallService.add(testPost)
        val updatedPost = newTestPost.copy(
            content = "updatedContent",
            likes = 1
        )

        val result = WallService.update(updatedPost)

        assertEquals(true, result)
    }

    @Test
    fun updateNonExisting() {

        val testPost = Post(
            authorId = 1,
            authorName = "testUser",
            content = "testContent",
            likes = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
            original = null,
            canPost = true
        )

        val newTestPost = WallService.add(testPost)

        val nonExistingPost = newTestPost.copy(
            postId = 10,
            content = "updatedContent",
            likes = 1
        )
        val result = WallService.update(nonExistingPost)

        assertEquals(false, result)

    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {

        val post = Post(
            postId = 999,
            authorId = 1,
            authorName = "Test",
            content = "Test",
            likes = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = null,
            replies = 0,
            original = null,
            canPost = true
        )
        WallService.add(post)
        val comment = Comment(2, "cool")

        WallService.createComment(3,comment)
    }

    @Test
    fun willDo() {

        val post = Post(
            authorId = 1,
            authorName = "Test",
            content = "Test",
            likes = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = null,
            replies = 0,
            original = null,
            canPost = true
        )
        WallService.add(post)
        val comment = Comment(2, "cool")

        val result = WallService.createComment(1,comment)
        assertEquals(comment, result)
    }
}
