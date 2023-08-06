package socialapp.com.example.SocialApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;

import java.util.Optional;

public interface SocialMediaUserRepo extends JpaRepository<SocialMediaUser,Long> {

    Boolean existsByEmail(String email);

    Optional<SocialMediaUser> findByUserNameOrEmail(String username, String email);

    Optional<SocialMediaUser> findByUserNameIgnoreCase(String userName);

    Boolean existsByUserNameIgnoreCase(String userName);

    void deleteByEmail(String email);

    Optional<SocialMediaUser> findByEmail(String email);
}
