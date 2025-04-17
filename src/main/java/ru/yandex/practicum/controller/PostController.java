package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.PostModel;
import ru.yandex.practicum.service.PostService;
import ru.yandex.practicum.util.Paging;
import org.springframework.data.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String posts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Model model) {

        // Obtener posts paginados y filtrados
        Page<PostModel> postPage =  postService.findPaginated(search, pageNumber, pageSize);

        // Configurar el objeto de paginación
        Paging paging = new Paging(
                postPage.getNumber() + 1, // pageNumber
                postPage.getSize(),      // pageSize
                postPage.getTotalPages(),
                postPage.hasNext(),
                postPage.hasPrevious()
        );

        // Agregar atributos al modelo
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("paging", paging);
        model.addAttribute("search", search);

        return "posts";
    }

    @GetMapping("/add")
    public String showAddForm() {
        return "redirect:/add-post";
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
