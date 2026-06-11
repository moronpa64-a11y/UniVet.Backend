package co.edu.uniremington.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "author")
    private String author;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @PrePersist
    protected void onCreate() {
        if (publishDate == null) publishDate = LocalDateTime.now();
    }
}
