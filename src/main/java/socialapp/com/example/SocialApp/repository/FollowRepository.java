package socialapp.com.example.SocialApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialapp.com.example.SocialApp.entity.Friends;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;

public interface FollowRepository  extends JpaRepository<Friends,Long> {

    Friends findByOwner(SocialMediaUser user);
}
