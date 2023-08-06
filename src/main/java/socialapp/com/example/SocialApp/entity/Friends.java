package socialapp.com.example.SocialApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JsonIgnore
    private SocialMediaUser owner;

    @ElementCollection(fetch = FetchType.LAZY)
    @ToString.Exclude
    Set<String> following = new HashSet<>();

    @ElementCollection
    @ToString.Exclude
    Set<String> follower = new HashSet<>();

}
