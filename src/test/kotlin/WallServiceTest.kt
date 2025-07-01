import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import ru.netology.Post
import ru.netology.WallService



class WallServiceTest {

    @Test
    fun add() {
        val testPost = Post(
            authorId = 1,
            authorName = "testUser",
            content = "testContent",
            likes = 0,
            comments = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
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
            comments = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
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
            comments = 0,
            date = System.currentTimeMillis(),
            replyOwnerId = 0,
            replies = 0,
            canPost = true
        )

        val newTestPost = WallService.add(testPost)

        // Пытаемся обновить несуществующий пост
        val nonExistingPost = newTestPost.copy(
                postId = 10, // Указываем ID, которого нет в сервисе
        content = "updatedContent",
        likes = 1
        )
        val result = WallService.update(nonExistingPost)

        assertEquals(false, result)

    }
}