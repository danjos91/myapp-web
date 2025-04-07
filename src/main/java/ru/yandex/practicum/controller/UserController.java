package ru.yandex.practicum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.yandex.practicum.model.User;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/users") // Контроллер обрабатывает запросы /users
public class UserController {

    @GetMapping // GET-запрос /users
    public String users() {
        return "users"; // Возвращаем название шаблона — users.html
    }

    @GetMapping("/users")
    public String users(Model model) {
        // Данные теперь получаются программно
        List<User> users = Arrays.asList(
                new User(1L, "Иван", "Иванов", 30, true),
                new User(2L, "Пётр", "Петров", 25, false),
                new User(3L, "Мария", "Сидорова", 28, true)
        );
        // Передаём данные в виде атрибута users
        model.addAttribute("users", users);

        return "users"; // Возвращаем название шаблона — users.html
    }

}