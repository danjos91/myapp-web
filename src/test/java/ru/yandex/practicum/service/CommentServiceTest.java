package ru.yandex.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.repository.CommentRepository;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @Transactional
    void addComment_shouldDelegateToRepository() {
        commentService.addComment(1L, "Test comment");

        verify(commentRepository).addComment(1L, "Test comment");
    }

    @Test
    @Transactional
    void deleteComment_shouldDelegateToRepository() {
        commentService.deleteComment(1L);

        verify(commentRepository).deleteComment(1L);
    }

    @Test
    @Transactional
    void editComment_shouldDelegateToRepository() {
        commentService.editComment(1L, "Updated");

        verify(commentRepository).updateComment(1L, "Updated");
    }
}