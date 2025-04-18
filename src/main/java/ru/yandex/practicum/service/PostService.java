package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.StorageException;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.PostRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final String uploadDir = "uploads/images/";

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    public Page<PostModel> getPosts(String search, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return (search != null && !search.trim().isEmpty())
                ? postRepository.findBySearch(search, pageable)
                : postRepository.findAll(pageable);
    }

    public PostModel getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Post not found with id: " + id));
    }

    public void createPost(String title, String text, MultipartFile image, String tags) throws IOException {
        PostModel post = new PostModel();
        post.setTitle(title);
        post.setText(text);
        post.setTags(tags);
        post.setLikes(0);
        post.setComments(new ArrayList<>());

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            post.setImagePath(imagePath);
        }

        postRepository.save(post);
    }

    public byte[] getPostImage(Long id) {
        PostModel post = getPostById(id);
        if (post.getImagePath() == null) {
            throw new NotFoundException("Image not found for post id: " + id);
        }
        Path path = Paths.get(post.getImagePath());
        try {
            if (post.getImagePath().equals("none")) {
                InputStream inputStream = getClass().getClassLoader()
                        .getResourceAsStream("/images/vacations.jpg");
                if (inputStream == null) {
                    throw new NotFoundException("Image not found");
                }
                return inputStream.readAllBytes();
            } else {
                byte[] bytes = Files.readAllBytes(path);
                return  bytes;
            }
        } catch (Exception e) {
            throw new StorageException("Error reading bytes", e);
        }
    }

    public void likePost(Long id, boolean like) {
        PostModel post = getPostById(id);
        post.setLikes(like ? post.getLikes() + 1 : post.getLikes() - 1);
        postRepository.update(post);
    }

    public void updatePost(Long id, String title, String text, MultipartFile image, String tags) throws IOException {
        PostModel post = getPostById(id);
        post.setTitle(title);
        post.setText(text);
        post.setTags(tags);

        if (image != null && !image.isEmpty()) {
            // Delete old image if exists
            if (post.getImagePath() != null) {
                Files.deleteIfExists(Paths.get(post.getImagePath()));
            }
            String imagePath = saveImage(image);
            post.setImagePath(imagePath);
        }

        postRepository.update(post);
    }


    public void deletePost(Long id) {
        PostModel post = getPostById(id);
        if (post.getImagePath() != null) {
            try {
                Files.deleteIfExists(Paths.get(post.getImagePath()));
            } catch (Exception e) {
                throw new StorageException("Delete post error", e);
            }

        }
        postRepository.deleteById(id);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(uploadDir + filename);
        Files.copy(image.getInputStream(), path);
        return path.toString();
    }

}