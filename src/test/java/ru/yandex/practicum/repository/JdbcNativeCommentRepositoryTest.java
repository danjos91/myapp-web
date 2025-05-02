package ru.yandex.practicum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.TestPropertySource;
import ru.yandex.practicum.configuration.DataSourceConfiguration;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.PostModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@TestPropertySource(locations = "classpath:test-application.properties")
class JdbcNativeCommentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private long postIdTest;


    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM comments");
        jdbcTemplate.execute("DELETE FROM posts");

        jdbcTemplate.execute("INSERT INTO posts (title, text, image_path, tags, likes) " +
                "VALUES ('Vacations1234', 'This is a post about wonderful vacations, when are u going on vacations',\n" +
                "         'none1', 'vacation, fun, rest, отдых, красиво', 0)");

        Pageable pageable = PageRequest.of(0, 10);
        PostModel posts = postRepository.findAll(pageable).getContent().getFirst();
        postIdTest = posts.getId();

        jdbcTemplate.execute("INSERT INTO comments (post_id, text) " +
                "VALUES ("+ postIdTest + ", 'This is a post about wonderful vacations, when are u going on vacations')");
        jdbcTemplate.execute("INSERT INTO comments (post_id, text) " +
                "VALUES ("+ postIdTest + ", 'This is a second post about wonderful vacations, when are u going on vacations')");

    }

    @Test
    void addComment_shouldPersistComment() {
        commentRepository.addComment(postIdTest, "Wonderful post");

        List<Comment> comments = commentRepository.findByPostId(postIdTest);
        assertEquals(3, comments.size());
        assertEquals("Wonderful post", comments.get(comments.size() - 1).getText());
    }

    @Test
    void deleteComment_shouldRemoveComment() {
        Comment comment = commentRepository.findByPostId(postIdTest).getFirst();
        commentRepository.deleteComment(comment.getId());

        List<Comment> comments = commentRepository.findByPostId(comment.getId());
        assertTrue(comments.isEmpty());
    }
}