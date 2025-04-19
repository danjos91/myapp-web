package ru.yandex.practicum.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.configuration.DataSourceConfiguration;
import ru.yandex.practicum.configuration.WebConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = {DataSourceConfiguration.class, WebConfiguration.class})
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test-application.properties")
class PostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
                .andExpect(model().attributeExists("posts"))
                .andExpect(xpath("//table/tbody/tr").nodeCount(2));
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
    void deletePost_shouldRemoveUserFromDatabaseAndRedirect() throws Exception {
        mockMvc.perform(delete("/posts/1")
                        .param("_method", "deletePost"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }
}
