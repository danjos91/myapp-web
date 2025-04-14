package ru.yandex.practicum.model;

import org.springframework.data.relational.core.sql.In;

import java.util.List;

public class PostModel {

    private Long id;
    private String title;
    private String text;
    private String shortDescription;
    private String imagePath; // Ruta relativa o absoluta al archivo TODO add logic to work with images
    private long likes;
    private List<String> comments;
    private List<String> tags;

    public PostModel(long id, String firstName, String lastName, int age, boolean active) {
    }

    public PostModel ();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public long getLikes() {
        return likes;
    }

    public void setLikes(long likes) {
        this.likes = likes;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
