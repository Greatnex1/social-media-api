package socialapp.com.example.SocialApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import socialapp.com.example.SocialApp.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
