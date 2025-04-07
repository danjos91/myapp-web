package ru.yandex.practicum.model;

public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private boolean active;

    // Конструктор без аргументов
    public User() {}

    // Конструктор с аргументами для удобства использования
    public User(Long id, String firstName, String lastName, int age, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.active = active;
    }

    // Геттеры и сеттеры ...

}