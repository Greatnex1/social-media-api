package socialapp.com.example.SocialApp.service;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import socialapp.com.example.SocialApp.email.EmailService;
import socialapp.com.example.SocialApp.entity.*;
import socialapp.com.example.SocialApp.dtos.UserDto;
import socialapp.com.example.SocialApp.dtos.UserProfileDto;
import socialapp.com.example.SocialApp.exception.SocialMediaUserException;
import socialapp.com.example.SocialApp.exception.UserAlreadyExistException;
import socialapp.com.example.SocialApp.repository.FollowRepository;
import socialapp.com.example.SocialApp.repository.RoleRepository;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;
import socialapp.com.example.SocialApp.repository.TokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.InvalidTransactionException;
import java.time.Instant;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SocialMediaUserServiceImpl implements SocialMediaUserService{
    private final SocialMediaUserRepo userRepository;

    private final TokenRepository tokenRepository;

    private final FollowRepository relationshipRepo;


    private final RoleRepository roleRepo;

    private final PasswordEncoder passwordEncoder;


    private final ModelMapper mapper;


    @Autowired
    JavaMailSender mailSender;

    private final EmailService emailService;


    @Override
    public SocialMediaUser registerUser(UserDto userDto, HttpServletRequest request) throws UserAlreadyExistException, MessagingException {
        if(userRepository.findByUserNameOrEmail(userDto.getUserName().toLowerCase(), userDto.getEmail().toLowerCase()).isPresent()){
            throw new UserAlreadyExistException("There is an account with this detail");
        }
        SocialMediaUser user = new SocialMediaUser();

        user.setUserName(userDto.getUserName().toLowerCase());

        user.setEmail(userDto.getEmail().toLowerCase());

        user.setDateCreated(Instant.now().toString());

       user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String role = "User";
        if(roleRepo.findByName(role) == null){
            user.getRoles().add(roleRepo.save(new Role("User")));
        }else {
            user.getRoles().add(roleRepo.findByName(role));
        }


        //token generation

        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken(token, user.getUserName());

        tokenRepository.save(verificationToken);

        //send email

        emailService.sendWelcomeMessage(userDto, verificationToken.getToken());

        userRepository.save(user);

        return user;
    }

    @Override
    public void verifyUser(String token) throws InvalidTransactionException, MessagingException {
        VerificationToken tokenizer = tokenRepository.findByToken(token)
                .orElseThrow(() -> new SocialMediaUserException("Token is invalid"));

        SocialMediaUser user = userRepository.findByUserNameIgnoreCase(tokenizer.getUser()).get();
        Calendar cal = Calendar.getInstance();
        if ((tokenizer.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(tokenizer);
            String newToken = UUID.randomUUID().toString();
            VerificationToken newVerificationToken = new VerificationToken(newToken, tokenizer.getUser());

            tokenRepository.save(newVerificationToken);
            emailService.newTokenMail(user.getEmail(), newToken);

            log.warn("Token has expired, please check your email for another token");
            throw new InvalidTransactionException("Token has expired, please check your email for another token");
        }else {
            userRepository.save(user);
            user.setEnabled(true);
        }
        tokenRepository.delete(tokenizer);

    }

    @Override
    public List<SocialMediaUser> findAllUser() {
        return userRepository.findAll();
    }


    @Override
    public SocialMediaUser updateProfile(Long userid, UserProfileDto profile) {
        SocialMediaUser user = userRepository.findById(userid)
                .orElseThrow(() -> new SocialMediaUserException("This user does not exist"));
       mapper.map(profile, user);
        //  user.setUpdated(Instant.now().toString());
        return userRepository.save(user);
    }

    @Override
    public Set<String> findAllFollowers(Long userId) {
        Friends follow = relationshipRepo.findById(userId).orElseThrow(() -> new SocialMediaUserException("This user has no relationship yet"));
        return follow.getFollower();
    }

    @Override
    public Set<String> findAllFollowing(Long id) {
        Friends friends = relationshipRepo.findById(id).orElseThrow(() ->new SocialMediaUserException("User does not exist") );

        return friends.getFollowing();
    }

    @Override
    public void deleteUser(String email) {
        SocialMediaUser user = userRepository.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new SocialMediaUserException("User with "+ email + " does not exist"));
        userRepository.deleteById(user.getId());

    }

    @Override
    public SocialMediaUser addFollower(String userName, String follower) {
        SocialMediaUser user = userRepository.findByUserNameIgnoreCase(userName)
                .orElseThrow(() -> new SocialMediaUserException("User does not exist"));
        Friends relationship = user.getRelationship();
        relationship.getFollower().add(follower);
        return userRepository.save(user);
    }

    @Override
    public SocialMediaUser addFollowing(String userName, String following) {
        SocialMediaUser user = userRepository.findByUserNameIgnoreCase(userName)
                .orElseThrow(() -> new SocialMediaUserException("User does not exist"));
        Friends relationship = user.getRelationship();
        relationship.getFollowing().add(following);
        return userRepository.save(user);
    }

    @Override
    public Optional<SocialMediaUser> findForumUser(Long id) {
        return userRepository.findById(id);}

    @Override
    public void unfollowUser(String userName, String follower) {
        try {
            SocialMediaUser user = userRepository.findByUserNameIgnoreCase(userName)
                    .orElseThrow(() -> new SocialMediaUserException("User does not exist"));
            Friends relationship = user.getRelationship();

            relationship.getFollowing().remove(follower);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @Override
    public SocialMediaUser findForumUser(String user_name) {
        return userRepository.findByUserNameIgnoreCase(user_name)
                .orElseThrow(() -> new SocialMediaUserException("User does not exist"));
    }
}
