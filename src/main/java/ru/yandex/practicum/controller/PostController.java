package ru.yandex.practicum.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.util.Paging;

import java.io.IOException;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String getPosts(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            Model model) {

        Page<PostModel> postPage = postService.getPosts(search, pageNumber, pageSize);
        Paging paging = new Paging(pageNumber, pageSize, postPage.hasNext(), postPage.hasPrevious());
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("search", search);
        model.addAttribute("paging", paging);

        return "posts";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/add")
    public String showAddPostForm(Model model) {
        model.addAttribute("post", new PostModel());
        return "add-post";
    }

    @PostMapping
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

    @PostMapping("/{id}/like")
    public String likePost(
            @PathVariable Long id,
            @RequestParam boolean like,
            RedirectAttributes redirectAttributes) {

        postService.likePost(id, like);
        redirectAttributes.addFlashAttribute("message", like ? "Post liked" : "Post unliked");
        return "redirect:/posts/" + id;
    }

    @GetMapping("/{id}/edit")
    public String showEditPostForm(@PathVariable Long id, Model model) {
        PostModel post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "add-post";
    }

    @PostMapping("/{id}")
    public String updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String text,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false, defaultValue = "") String tags,
            RedirectAttributes redirectAttributes) throws IOException {

        postService.updatePost(id, title, text, image, tags);
        redirectAttributes.addFlashAttribute("message", "Post updated successfully");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments")
    public String addComment(
            @PathVariable Long id,
            @RequestParam String text,
            RedirectAttributes redirectAttributes) {

        postService.addComment(id, text);
        redirectAttributes.addFlashAttribute("message", "Comment added");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments/{commentId}")
    public String editComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            @RequestParam String text,
            RedirectAttributes redirectAttributes) {

        postService.editComment(id, commentId, text);
        redirectAttributes.addFlashAttribute("message", "Comment updated");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/comments/{commentId}/delete")
    public String deleteComment(
            @PathVariable Long id,
            @PathVariable Long commentId,
            RedirectAttributes redirectAttributes) {

        postService.deleteComment(id, commentId);
        redirectAttributes.addFlashAttribute("message", "Comment deleted");
        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deletePost(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        postService.deletePost(id);
        redirectAttributes.addFlashAttribute("message", "Post deleted");
        return "redirect:/posts";
    }
}