package ru.yandex.practicum.model;

import lombok.Data;

@Data
public class Tag {
    Long id;
    Long postId;
    String text;
}
