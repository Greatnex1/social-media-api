package socialapp.com.example.SocialApp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 3000, nullable = false)
    private String content;


    @ElementCollection(fetch = FetchType.EAGER)
    Set<String> numberOfLikes = new HashSet<>();

    private String videoUrl;

    //  private String imageUrl;


    private String dateCreated;

    private String dateUpdated;

    private String postUser;
}
