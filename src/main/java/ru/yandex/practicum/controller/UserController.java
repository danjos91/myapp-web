package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.User;
import ru.yandex.practicum.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping // GET запрос /users
    public String users(Model model) {
        List<User> users = service.findAll();
        model.addAttribute("users", users);

        return "users"; // Возвращаем название шаблона — users.html
    }

    @PostMapping
    public String save(@ModelAttribute User user) {
        service.save(user);

        return "redirect:/users"; // Возвращаем страницу, чтобы она перезагрузилась
    }

    @PostMapping(value = "/{id}", params = "_method=delete")
    public String delete(@PathVariable(name = "id") Long id) {
        service.deleteById(id);

        return "redirect:/users";
    }

}