package ru.yandex.practicum.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository1) {
        this.commentRepository = commentRepository1;
    }

    public void addComment(Long postId, String text) {;
        commentRepository.addComment(postId, text);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteComment(commentId);
    }

    public List<Comment> getCommentsByPostId(Long id) {
       return commentRepository.findByPostId(id);
    }


    public void editComment(Long commentId, String text) {
        commentRepository.updateComment(commentId, text);
    }
}
