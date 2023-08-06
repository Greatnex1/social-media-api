package socialapp.com.example.SocialApp.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import socialapp.com.example.SocialApp.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.postUser= ?1")
    List<Post> findByPostUserIgnoreCase(String user);

    List<Post> findByPostUserIgnoreCaseOrderByDateCreated(String user);


    List<Post> findAllByOrderByDateCreatedDesc();


    void deleteById(Long id);

    @NonNull
    Optional<Post> findById(Long id);
}
