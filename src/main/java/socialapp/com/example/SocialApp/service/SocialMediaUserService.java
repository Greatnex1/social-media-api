package socialapp.com.example.SocialApp.service;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import socialapp.com.example.SocialApp.dtos.UserDto;
import socialapp.com.example.SocialApp.dtos.UserProfileDto;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.exception.UserAlreadyExistException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.InvalidTransactionException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SocialMediaUserService {


    SocialMediaUser registerUser(UserDto userDto, HttpServletRequest request) throws UserAlreadyExistException, MessagingException;

    void verifyUser(String token) throws InvalidTransactionException, MessagingException;

    List<SocialMediaUser> findAllUser();

    SocialMediaUser updateProfile(Long userid, UserProfileDto profile);

    Set<String> findAllFollowers(Long userId);

    Set<String> findAllFollowing(Long id);

    void deleteUser(String email);

    SocialMediaUser addFollower(String userName, String follower);

    SocialMediaUser  addFollowing(String userName, String following);

    public Optional<SocialMediaUser> findForumUser(Long id);
    void unfollowUser(String userName, String follower);

    public void deleteAllUser();

    SocialMediaUser findForumUser(String user_name);



}
