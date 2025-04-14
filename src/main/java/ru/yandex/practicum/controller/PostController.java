package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.service.PostService;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping // GET запрос /posts
    public String posts(PostModel postModel) {
        List<PostModel> posts = postService.findAll();
        postModel.addAttribute("posts", posts);

        return "posts"; // Возвращаем название шаблона — posts.html
    }

    @PostMapping
    public String save(@ModelAttribute PostModel postModel) {
        postService.save(postModel);

        return "redirect:/posts"; // Возвращаем страницу, чтобы она перезагрузилась
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable(name = "id") Long id) {
        postService.deleteById(id);

        return "redirect:/users";
    }

    @GetMapping(value = "/{id}", params = "_method=delete")
    public String getById(@PathVariable(name = "id") Long id) {
        postService.getById(id);

        return "redirect:/post/{id}";
    }

}