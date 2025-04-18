package ru.yandex.practicum.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.Comment;

import java.util.Collections;
import java.util.List;

@Repository
public class JdbcNativeCommentRepository implements CommentRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativeCommentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addComment(long postId, String comment) {
        jdbcTemplate.update(
                "INSERT INTO comments (post_id, text) VALUES(?, ?)",
                postId, comment);
    }

    @Override
    public void deleteComment(long id) {
        int rowsAffected = jdbcTemplate.update(
                "DELETE FROM comments WHERE id = ?",
                id
        );
    }

    @Override
    public List<Comment> findByPostId(Long id) {
        if (id == null) {
            return Collections.emptyList(); // or throw IllegalArgumentException
        }
        return jdbcTemplate.query(
                "select * from comments where post_id = ?",
                (rs, rowNum) -> new Comment(
                        rs.getLong("id"),
                        rs.getLong("post_id"),
                        rs.getString("text")
                ),
                id
        );
    }

    @Override
    public void updateComment(Long commentId, String text) {
        jdbcTemplate.update(
                "UPDATE comments SET text = ? WHERE id = ?",
                text, commentId
        );
    }
}
