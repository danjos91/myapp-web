package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.PostModel;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    List<PostModel> findAll();

    Optional<PostModel> findById(long id);

    void save(PostModel postModel);

    void deleteById(Long id);
}