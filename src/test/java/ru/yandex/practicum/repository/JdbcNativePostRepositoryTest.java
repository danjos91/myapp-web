package ru.yandex.practicum.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.yandex.practicum.configuration.DataSourceConfiguration;
import ru.yandex.practicum.model.PostModel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, JdbcNativePostRepository.class})
@TestPropertySource(locations = "classpath:test-application.properties")
class JdbcNativePostRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        // Очистка базы данных
        jdbcTemplate.execute("DELETE FROM posts");

        // Добавление тестовых данных
        jdbcTemplate.execute("INSERT INTO posts (title, text, image_path, tags, likes)\n" +
                "VALUES ('Vacations1', 'This is a post about wonderful vacations, when are u going on vacations',\n" +
                "         'none1', 'vacation, fun, rest, отдых, красиво', 0)");
        jdbcTemplate.execute("INSERT INTO posts (title, text, image_path, tags, likes)\n" +
                "VALUES ('Work', 'This is a post about wonderful vacations, when are u going on vacations',\n" +
                "        'none3', 'vacation, fun, rest, отдых, красиво', 0)");
        jdbcTemplate.execute("INSERT INTO posts (title, text, image_path, tags, likes)\n" +
                "VALUES ('Hobbies', 'This is a post about wonderful vacations, when are u going on vacations',\n" +
                "        'none3', 'hobby', 0)");
    }

    @Test
    void save_shouldAddUserToDatabase() {
        PostModel postModel = new PostModel();
        postModel.setTitle("Test");
        postModel.setText("Text test");
        postModel.setImagePath("none3");
        postModel.setTags("nice, test");
        postModel.setLikes(10);

        postRepository.save(postModel);

        Pageable pageable = PageRequest.of(0, 10);
        PostModel savedPostModel = postRepository.findAll(pageable).getContent().getLast();

        assertNotNull(savedPostModel);
        assertEquals("Test", savedPostModel.getTitle());
        assertTrue(savedPostModel.getTags().contains("nice"));
    }

    @Test
    void findAll_shouldReturnAllPosts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<PostModel> posts = postRepository.findAll(pageable).getContent();

        assertNotNull(posts);
        assertEquals(3, posts.size());

        PostModel postModel = posts.getFirst();
        assertEquals("Vacations1", postModel.getTitle());
    }

    @Test
    void deleteById_shouldRemoveUserFromDatabase() {
        postRepository.deleteById(1L);

        Pageable pageable = PageRequest.of(0, 10);
        List<PostModel> posts = postRepository.findAll(pageable).getContent();

        PostModel deletedPost = posts.stream()
                .filter(deletedPosts -> deletedPosts.getId().equals(1L))
                .findFirst()
                .orElse(null);
        assertNull(deletedPost);
    }

    @Test
    void findBySearch_shouldPreventSqlInjection() {
        String maliciousInput = "'; DELETE FROM posts; --";

        Pageable pageable = PageRequest.of(0, 10);
        postRepository.findBySearch(maliciousInput, pageable);
        List<PostModel> posts = postRepository.findAll(pageable).getContent();

        assertTrue(posts.size() > 0);
    }
}
