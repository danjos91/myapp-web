package ru.yandex.practicum.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.model.User;

import java.util.List;

@Repository
public class JdbcNativePostRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcNativePostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAll() {
        // Выполняем запрос с помощью JdbcTemplate
        // Преобразовываем ответ с помощью RowMapper
        return jdbcTemplate.query(
                "select id, title from users",
                (rs, rowNum) -> new PostModel(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getBoolean("active")
                ));
    }

    @Override
    public void save(User user) {
        // Формируем insert-запрос с параметрами
        jdbcTemplate.update("insert into users(first_name, last_name, age, active) values(?, ?, ?, ?)",
                user.getFirstName(), user.getLastName(), user.getAge(), user.isActive());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from users where id = ?", id);
    }

}