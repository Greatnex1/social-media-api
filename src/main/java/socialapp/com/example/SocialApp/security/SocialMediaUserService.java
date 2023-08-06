package socialapp.com.example.SocialApp.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import socialapp.com.example.SocialApp.entity.AuthenticationType;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;
import java.util.ArrayList;
import java.util.Collection;


@Service
@RequiredArgsConstructor
@Slf4j
public class SocialMediaUserService implements UserDetailsService {
    private final SocialMediaUserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        try {
            SocialMediaUser user = userRepo.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
                    new UsernameNotFoundException("User not found with username or email:" + usernameOrEmail));

            if (!user.isEnabled()) {
                throw new IllegalAccessException("User has not been verified");
            } else {
                log.info("User found in the database: {}", usernameOrEmail);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                });
                return new User(user.getUserName(), user.getPassword(), authorities);
            }
        } catch (UsernameNotFoundException | IllegalAccessException e) {
            log.error(e.getLocalizedMessage());
            ResponseEntity.badRequest().body(e.getMessage());
            return null;
        }
    }

    public void createNewClientAfterOauthLoginSuccess(String email, AuthenticationType authenticationProvider){
        SocialMediaUser  platformUser = new SocialMediaUser();

        platformUser.setEmail(email);
        platformUser.setEnabled(true);
//        platformUser.setDateCreated("");
        platformUser.setAuthType(authenticationProvider);

        userRepo.save(platformUser);

    }
    public void updateCustomerAfterOauthLoginSuccess(String email, AuthenticationType authenticationProvider){
      SocialMediaUser platformUser = new SocialMediaUser();
        platformUser.setEmail(email);
        platformUser.setEnabled(true);
//        platformUser.setDateCreated("");
        platformUser.setAuthType(authenticationProvider);


        userRepo.save(platformUser);
    }
}

