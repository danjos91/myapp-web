package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users") // Контроллер обрабатывает запросы /users
public class UserController {

    @GetMapping // GET-запрос /users
    public String users() {
        return "users"; // Возвращаем название шаблона — users.html
    }

}