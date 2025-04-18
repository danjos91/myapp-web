package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.model.PostModel;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Page<PostModel> findAll(Pageable pageable);

    Page<PostModel> findBySearch(String search, Pageable pageable);

    List<PostModel> findAll();

    Optional<PostModel> findById(long id);

    void save(PostModel postModel);

    void update(PostModel postModel);

    void deleteById(Long id);
}