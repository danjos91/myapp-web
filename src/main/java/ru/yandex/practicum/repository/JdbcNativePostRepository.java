package ru.yandex.practicum.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.PostModel;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcNativePostRepository implements PostRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PostModel> findAll() {
        String sql = "SELECT * FROM posts";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new PostModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getString("short_description"),
                rs.getString("image_path"),
                rs.getLong("likes")
        ));
    }

    @Override
    public Optional<PostModel> findById() {
        return Optional.empty();
    }

    @Override
    public Optional<PostModel> findById(long id) {
        String sql = "SELECT * FROM posts WHERE id = ?";

        // Definir RowMapper como variable separada
        RowMapper<PostModel> rowMapper = (rs, rowNum) -> new PostModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getString("short_description"),
                rs.getString("image_path"),
                rs.getLong("likes")
        );

        try {
            PostModel post = jdbcTemplate.queryForObject(sql, rowMapper, id);
            return Optional.ofNullable(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(PostModel postModel) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into posts(title, text, short_description, image_path, likes) values(?, ?, ?, ?)",
                postModel.getTitle(), postModel.getText(), postModel, postModel.getShortDescription(), postModel.getImagePath(), postModel.getLikes());
    }


    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from posts where id = ?", id);
    }


    @Override
    public Page<PostModel> findAll(Pageable pageable) {

        RowMapper<PostModel> rowMapper = (rs, rowNum) -> new PostModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getString("short_description"),
                rs.getString("image_path"),
                rs.getLong("likes")
        );

        String countSql = "SELECT COUNT(*) FROM posts";

        List<PostModel> content = jdbcTemplate.query("SELECT * FROM posts", rowMapper);
        Long total = jdbcTemplate.queryForObject(countSql, Long.class);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PostModel> findBySearch(String search, Pageable pageable) {

        String sql = String.format(
                "SELECT * FROM posts WHERE LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?) LIMIT %d",
                pageable.getPageSize()
        );

        RowMapper<PostModel> rowMapper = (rs, rowNum) -> new PostModel(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getString("short_description"),
                rs.getString("image_path"),
                rs.getLong("likes")
        );

        String countSql = "SELECT COUNT(*) FROM posts WHERE LOWER(title) LIKE LOWER(?) OR LOWER(content) LIKE LOWER(?)";

        String searchTerm = "%" + search + "%";
        List<PostModel> content = jdbcTemplate.query(sql, rowMapper, searchTerm, searchTerm);
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, searchTerm, searchTerm);

        return new PageImpl<>(content, pageable, total);
    }


    public Page<PostModel> findByTagsContaining(String tag, Pageable pageable) {
        return null;
    }

}