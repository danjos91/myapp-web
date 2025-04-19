package ru.yandex.practicum.model;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class PostModel {

    private Long id;
    private String title = "";
    private String text = "";
    private String shortDescription = "";
    private String imagePath;
    private long likes = 0;
    private List<Comment> comments = new ArrayList<>();
    private String tags = "";

    public PostModel(Long id, String title, String text, String shortDescription, String imagePath, String tags, long likes) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.shortDescription = shortDescription;
        this.imagePath = imagePath;
        this.tags = tags;
        this.likes = likes;
    }

    public PostModel() {
    }

    public List<String> getTagsAsList() {
        return Arrays.stream(tags.split(",")).toList();
    }

    public String getTextPreview() {
        String[] textSplited = text.split(" ");
        StringBuilder textPreview = new StringBuilder();
        int limit = Math.min(textSplited.length, 10);
        for (int i=0; i< limit; i++) {
            textPreview.append(textSplited[i]).append(" ");
        }
        textPreview.append("...");
        return textPreview.toString();
    }

}

