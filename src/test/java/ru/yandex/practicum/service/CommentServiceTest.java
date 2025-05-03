package ru.yandex.practicum.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.CommentRepository;
import ru.yandex.practicum.repository.PostRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        PostModel post = new PostModel();
        post.setTitle("Test Post");
        post.setText("Test Content");
        post.setImagePath("none");
        post.setTags("test");
        postRepository.save(post);
    }

    @Test
    void addComment_shouldSaveComment() {
        Pageable pageable = PageRequest.of(0, 10);
        PostModel posts = postRepository.findAll(pageable).getContent().getFirst();
        Long existingPostId = posts.getId();
        assertDoesNotThrow(() -> {
            commentService.addComment(existingPostId, "Test comment");
            assertFalse(commentRepository.findByPostId(existingPostId).isEmpty());
        });
    }

    @Test
    void deleteComment_shouldRemoveComment() {
        Pageable pageable = PageRequest.of(0, 10);
        PostModel posts = postRepository.findAll(pageable).getContent().getFirst();
        Long existingPostId = posts.getId();
        commentService.addComment(existingPostId, "To be deleted");
        Long commentId = commentRepository.findByPostId(existingPostId).getFirst().getId();
        assertDoesNotThrow(() -> commentService.deleteComment(commentId));
        assertTrue(commentRepository.findByPostId(existingPostId).isEmpty());
    }

    @Test
    void editComment_shouldUpdateComment() {
        Pageable pageable = PageRequest.of(0, 10);
        PostModel posts = postRepository.findAll(pageable).getContent().getFirst();
        Long existingPostId = posts.getId();
        commentService.addComment(existingPostId, "To be updated");
        Long commentId = commentRepository.findByPostId(existingPostId).getFirst().getId();
        assertDoesNotThrow(() -> commentService.editComment(commentId, "Updated text"));
        assertEquals("Updated text",
                commentRepository.findByPostId(existingPostId).getFirst().getText());
    }
}