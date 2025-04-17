package ru.yandex.practicum.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name = "posts")
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String text;
    private String shortDescription;
    private String imagePath; // relative path TODO add logic to work with images
    private long likes;
    private List<Comment> comments;
    private List<Tag> tags;

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

