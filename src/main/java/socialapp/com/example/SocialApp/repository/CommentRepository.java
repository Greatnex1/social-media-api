package socialapp.com.example.SocialApp.repository;

import org.springframework.data.repository.CrudRepository;
import socialapp.com.example.SocialApp.entity.Comment;

public interface CommentRepository extends CrudRepository<Comment,Long> {
}
