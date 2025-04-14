package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.repository.PostRepository;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

    public void save(PostModel postModel) {
        postRepository.save(postModel);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}