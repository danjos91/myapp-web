package ru.yandex.practicum.model;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostModel {

    private Long id;
    private String title = "";
    private String text = "";
    private String shortDescription = "";
    private String imagePath; // relative path TODO add logic to work with images
    private long likes = 0;
    private List<Comment> comments = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

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

    public String getTagsAsText() {
        String tagsAsStr = "";
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                tagsAsStr = tag + ", ";
            }
        }

        return tagsAsStr;
    }
}

