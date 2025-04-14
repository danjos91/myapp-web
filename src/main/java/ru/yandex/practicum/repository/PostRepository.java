package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.PostModel;
import java.util.List;

public interface PostRepository {
    List<PostModel> findAll();

    PostModel findById();

    void save(PostModel postModel);

    void deleteById(Long id);
}