package ru.yandex.practicum.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Очистка и заполнение тестовых данных в базе
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("INSERT INTO posts(title, text, image_path, tags, likes)" +
                "VALUES ('Vacations1', 'This is a post about wonderful vacations, when are u going on vacations'," +
                "'none1', 'vacation, fun, rest, отдых, красиво', 0)");
        jdbcTemplate.execute("INSERT INTO posts(title, text, image_path, tags, likes)" +
                "VALUES ('Work', 'This is a post about my interesting work, what is your work about?'," +
                "'none3', 'work, business', 0)");
    }

    @Test
    void getPosts_shouldReturnHtmlWithPosts() throws Exception {
        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("posts"))
                .andExpect(model().attributeExists("posts"));
    }

    @Test
    void addPost_shouldAddPostToDatabaseAndRedirect() throws Exception {
        mockMvc.perform(post("/posts")
                        .param("title", "Hobbies")
                        .param("text", "This is a post about hobbies, this is an example")
                        .param("image", "none1")
                        .param("tags", "hobby, interesting"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void delete_shouldRemoveUserFromDatabaseAndRedirect() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        PostModel posts = postRepository.findAll(pageable).getContent().getFirst();
        String index = posts.getId().toString();
        mockMvc.perform(post("/posts/" + index + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    void updatePost_shouldModifyExistingPost() throws Exception {
        PostModel post = postRepository.findAll(PageRequest.of(0, 1)).getContent().getFirst();

        mockMvc.perform(post("/posts/" + post.getId())
                        .param("title", "New Title")
                        .param("text", "Updated text")
                        .param("tags", "updated, new"))
                .andExpect(status().is3xxRedirection());

        PostModel updated = postRepository.findById(post.getId()).get();
        assertEquals("New Title", updated.getTitle());
        assertEquals("Updated text", updated.getText());
    }
}
