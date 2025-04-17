package ru.yandex.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.PostRepository;
import java.util.List;

@Service
public class PostService {


    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostModel> findPaginated(String search, int pageNumber, int pageSize) {

        // Convert 1-based page number to 0-based
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

        if (search != null && !search.trim().isEmpty()) {
            return postRepository.findBySearch(search, pageable);
        }
        return postRepository.findAll(pageable);
    }

    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

    public void save(PostModel postModel) {
        postRepository.save(postModel);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void getById(Long id) {
        postRepository.findById(id);
    }
}
