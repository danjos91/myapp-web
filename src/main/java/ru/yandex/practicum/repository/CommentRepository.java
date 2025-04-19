package ru.yandex.practicum.repository;

import ru.yandex.practicum.model.Comment;

import java.util.List;

public interface CommentRepository {

    void addComment(long postId, String comment);

    void deleteComment(long postId);

    List<Comment> findByPostId(Long id);

    void updateComment(Long commentId, String text);
}