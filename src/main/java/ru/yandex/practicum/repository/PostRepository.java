package ru.yandex.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.PostModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {

    // Basic pagination
    Page<PostModel> findAll(Pageable pageable);

    // Search with pagination (title or content)
    @Query("SELECT p FROM PostModel p WHERE " +
            "(:search IS NULL OR :search = '' OR " +
            "LOWER(p.title) ILIKE LOWER(concat('%', :search, '%')) OR " +
            "LOWER(p.text) ILIKE LOWER(concat('%', :search, '%')))")
    Page<PostModel> findBySearch(
            @Param("search") String search,
            Pageable pageable);


    List<PostModel> findAll();

    Optional<PostModel> findById();

    Optional<PostModel> findById(long id);

    void save(PostModel postModel);

    void deleteById(Long id);
}