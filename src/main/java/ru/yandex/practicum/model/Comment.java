package ru.yandex.practicum.model;

import lombok.Data;

@Data
public class Comment {
    Long id;
    Long postId;
    String text;
}
