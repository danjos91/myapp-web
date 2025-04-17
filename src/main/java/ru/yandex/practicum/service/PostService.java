package ru.yandex.practicum.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.yandex.practicum.exception.NotFoundException;
import ru.yandex.practicum.exception.StorageException;
import ru.yandex.practicum.model.Comment;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.repository.PostRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public PostModel createPost(String title, String text, MultipartFile image, String tags) throws IOException {
        PostModel post = new PostModel();
        post.setTitle(title);
        post.setText(text);
        post.setTags(parseTags(tags));
        post.setLikes(0);
        post.setComments(new ArrayList<>());

        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            post.setImagePath(imagePath);
        }

        postRepository.save(post);
        return post;
    }

    public byte[] getPostImage(Long id) {
        PostModel post = getPostById(id);
        if (post.getImagePath() == null) {
            throw new NotFoundException("Image not found for post id: " + id);
        }
        Path path = Paths.get(post.getImagePath());
        try {
            return   Files.readAllBytes(path);
        } catch (Exception e) {
            throw new StorageException("Error reading bytes", e);
        }

    }

    public void likePost(Long id, boolean like) {
        PostModel post = getPostById(id);
        post.setLikes(like ? post.getLikes() + 1 : post.getLikes() - 1);
        postRepository.save(post);
    }

    public void updatePost(Long id, String title, String text, MultipartFile image, String tags) throws IOException {
        PostModel post = getPostById(id);
        post.setTitle(title);
        post.setText(text);
        post.setTags(parseTags(tags));

        if (image != null && !image.isEmpty()) {
            // Delete old image if exists
            if (post.getImagePath() != null) {
                Files.deleteIfExists(Paths.get(post.getImagePath()));
            }
            String imagePath = saveImage(image);
            post.setImagePath(imagePath);
        }

        postRepository.save(post);
    }

    public void addComment(Long id, String text) {
        PostModel post = getPostById(id);
        Comment comment = new Comment();
        comment.setText(text);
        post.getComments().add(comment);
        postRepository.save(post);
    }

    public void editComment(Long id, Long commentId, String text) {
        PostModel post = getPostById(id);
        Optional<Comment> comment = post.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst();
        if (comment.isPresent()) {
            comment.get().setText(text);
            postRepository.save(post);
        }
    }

    public void deleteComment(Long id, Long commentId) {
        PostModel post = getPostById(id);
        post.getComments().removeIf(c -> c.getId().equals(commentId));
        postRepository.save(post);
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

    private List<String> parseTags(String tags) {
        if (tags == null || tags.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return List.of(tags.split(",\\s*"));
    }
}