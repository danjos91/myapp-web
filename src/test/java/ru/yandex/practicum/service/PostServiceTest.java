package ru.yandex.practicum.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.PostRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private PostService postService;

    @Test
    @Transactional(readOnly = true)
    void getPostById_shouldReturnPostWhenExists() {
        PostModel expected = new PostModel(1L, "Test", "Content", "none", "new, test", 10);
        when(postRepository.findById(1L)).thenReturn(Optional.of(expected));

        PostModel result = postService.getPostById(1L);

        assertEquals(expected, result);
    }

    @Test
    @Transactional(readOnly = true)
    void getPostById_shouldThrowWhenNotExists() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> postService.getPostById(1L));
    }

    @Test
    @Transactional
    void deletePost_shouldDeleteWhenImageIsDefault() {
        PostModel post = new PostModel(1L, "Test", "Content", "none", "new, test", 10);
        post.setImagePath("none1");
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));

        postService.deletePost(1L);

        verify(postRepository).deleteById(1L);
    }
}
