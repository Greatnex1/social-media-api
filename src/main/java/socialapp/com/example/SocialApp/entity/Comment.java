package socialapp.com.example.SocialApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "comment")
    @ToString.Exclude
    private List<Reply> replies = new ArrayList<>();

    private String commenter;

    private int likes;


    private String dateCreated;


    private String dateUpdated;

    private String content;

    @ManyToOne
    @JsonIgnore
    private Post post;

}
