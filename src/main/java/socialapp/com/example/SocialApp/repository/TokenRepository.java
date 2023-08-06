package socialapp.com.example.SocialApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.entity.VerificationToken;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

    Boolean existsByToken(String token);
    Optional<VerificationToken> findByToken(String token);

    VerificationToken findByUser(SocialMediaUser user);
}
