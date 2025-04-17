package ru.yandex.practicum.model;
import lombok.Data;
import java.util.List;

@Data
public class PostModel {

    private Long id;
    private String title;
    private String text;
    private String shortDescription;
    private String imagePath; // relative path TODO add logic to work with images
    private long likes;
    private List<Comment> comments;
    private List<String> tags;

    public PostModel(Long id, String title, String text, String shortDescription, String imagePath, long likes) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.shortDescription = shortDescription;
        this.imagePath = imagePath;
        this.likes = likes;
    }

    public PostModel() {

    }
}

