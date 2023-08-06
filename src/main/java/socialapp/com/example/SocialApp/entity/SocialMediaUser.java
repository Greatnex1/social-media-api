package socialapp.com.example.SocialApp.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class SocialMediaUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userName;

    private String email;

    private String profilePicture;

    private String password;

    @OneToMany
    @JoinColumn(name = "socialmedia_user_id")
    @ToString.Exclude
    Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Post> postList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne(mappedBy = "owner")
    private Friends relationship;

    @Enumerated(EnumType.STRING)
    private AuthenticationType authType;


    private boolean active;

    private boolean online;

    private boolean confirmed;

    private boolean enabled;

    private String lastLogin;




}
