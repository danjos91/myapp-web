package ru.yandex.practicum.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String getPosts(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            Model model) {

        Page<PostModel> postPage = postService.getPosts(search, pageNumber, pageSize);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("paging", Map.of(
                "pageNumber", pageNumber,
                "pageSize", pageSize,
                "hasNext", postPage.hasNext(),
                "hasPrevious", postPage.hasPrevious()
        ));

        return "posts";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/posts/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new PostModel());
        return "add-post";
    }

    @PostMapping("/posts")
    public String addPost(
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam MultipartFile image,
            @RequestParam(required = false, defaultValue = "") String tags,
            RedirectAttributes redirectAttributes) throws IOException {

        PostModel post = postService.createPost(title, text, image, tags);
        redirectAttributes.addFlashAttribute("message", "Post created successfully");
        return "redirect:/posts/" + post.getId();
    }

    @GetMapping("/images/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        return postService.getPostImage(id);
    }

    @PostMapping("/posts/{id}/like")
    public String likePost(
            @PathVariable Long id,
            @RequestParam boolean like,
            RedirectAttributes redirectAttributes) {

        postService.likePost(id, like);
        redirectAttributes.addFlashAttribute("message", like ? "Post liked" : "Post unliked");
        return "redirect:/posts/" + id;
    }

    @GetMapping("/posts/{id}/edit")
    public String showEditPostForm(@PathVariable Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping("/posts/{id}")
    public String updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false, defaultValue = "") String tags,
            RedirectAttributes redirectAttributes) throws IOException {

        PostModel post = postService.updatePost(id, title, text, image, tags);
        redirectAttributes.addFlashAttribute("message", "Post updated successfully");
        return "redirect:/posts/" + post.getId();
    }

    @PostMapping("/posts/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @RequestParam String text,
            RedirectAttributes redirectAttributes) {

        postService.addComment(id, text);
        redirectAttributes.addFlashAttribute("message", "Comment added");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}")
    public String editComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @RequestParam String text,
            RedirectAttributes redirectAttributes) {

        postService.editComment(id, commentId, text);
        redirectAttributes.addFlashAttribute("message", "Comment updated");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            RedirectAttributes redirectAttributes) {

        postService.deleteComment(id, commentId);
        redirectAttributes.addFlashAttribute("message", "Comment deleted");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        postService.deletePost(id);
        redirectAttributes.addFlashAttribute("message", "Post deleted");
        return "redirect:/posts";
    }
}