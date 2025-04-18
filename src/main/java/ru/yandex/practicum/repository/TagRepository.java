package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.Comment;

import java.util.List;

public interface TagRepository {

    void addTag(long postId, String comment);

    void deleteTag(long postId);

    List<Comment> findByPostId(Long id);

    void updateTag(Long commentId, String text);
}