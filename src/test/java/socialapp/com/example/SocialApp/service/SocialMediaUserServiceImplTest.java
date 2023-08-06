package socialapp.com.example.SocialApp.service;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.UserConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import socialapp.com.example.SocialApp.dtos.UserDto;
import socialapp.com.example.SocialApp.dtos.UserProfileDto;
import socialapp.com.example.SocialApp.email.EmailService;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.exception.UserAlreadyExistException;
import socialapp.com.example.SocialApp.repository.FollowRepository;
import socialapp.com.example.SocialApp.repository.RoleRepository;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;
import socialapp.com.example.SocialApp.repository.TokenRepository;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SocialMediaUserServiceImplTest {

    @Mock
  SocialMediaUserRepo platformUserRepository;

    @InjectMocks
    private  SocialMediaUserServiceImpl platformUser;

    @Mock
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;


    private UserConfig userConfig;
    @Mock
    private FollowRepository relationshipRepo;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmailService emailService;
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private HttpServletRequest servletRequest;

  @BeforeEach
    void up(){
        platformUser = new SocialMediaUserServiceImpl(platformUserRepository,tokenRepository,
                relationshipRepo,roleRepository,passwordEncoder,modelMapper,emailService);


    }


    @Test
    void UserCanBeSaved() throws UserAlreadyExistException, MessagingException {

      UserDto user =new UserDto();

      user.setEmail("sel@mail.com");
      user.setPassword("1234");
    SocialMediaUser platuser =new SocialMediaUser();
      user.setUserName("sella");
      platuser.setId(2L);
     // platuser.setFirstName(user.getUserName());
      platuser.setPassword(user.getPassword());

      platuser.setPassword(passwordEncoder.encode(user.getPassword()));
      platformUser.registerUser(user,servletRequest);

      ArgumentCaptor<SocialMediaUser> userDtoArgumentCaptor = ArgumentCaptor.forClass(SocialMediaUser.class);
      verify(platformUserRepository).save(userDtoArgumentCaptor.capture());
      log.info("user->{}",user.getUserName());
      log.info("user id ->{}",platuser.getId());
    }

    @Test
    void verifyUser() {
    }

    @Test
    void findAllUser() {
      platformUser.findAllUser();
      verify(platformUserRepository).findAll();
      assertThat(platformUserRepository).isNotNull();
      log.info("User in Repo ->{}",platformUserRepository.findAll());
    }

    @Test
    void updateProfile() {
      UserProfileDto user = new UserProfileDto();
      SocialMediaUser platuser =new SocialMediaUser();
      platuser.setId(1L);
      user.setUserName("sella");
      user.setCountry("Nigeria");
      platuser.setUserName(user.getUserName());
      log.info("updated username ->{}",platuser.getUserName());
    }

    @Test
    void findAllFollowers() {

      platformUser.findAllFollowers(1L);
      verify(platformUserRepository).findByUserNameOrEmail("ella","sel@mail.com");
      assertThat(platformUserRepository).isNotNull();
      log.info("Followers ->{}",platformUserRepository.findAll());
    }

    @Test
    void findAllFollowing() {
    }

    @Test
    void deleteUserAccount() throws UserAlreadyExistException, MessagingException {

      UserDto user =new UserDto();

      user.setEmail("sel@mail.com");
      user.setPassword("1234");
     SocialMediaUser platuser =new SocialMediaUser();
      user.setUserName("sella");
      platuser.setId(2L);
      platuser.setEmail("sel@mail.com");
    //  platuser.setFirstName(user.getUserName());
      platuser.setPassword(user.getPassword());

      platuser.setPassword(passwordEncoder.encode(user.getPassword()));
      platformUser.registerUser(user,servletRequest);
      ArgumentCaptor<SocialMediaUser> userDtoArgumentCaptor = ArgumentCaptor.forClass(SocialMediaUser.class);
      verify(platformUserRepository).save(userDtoArgumentCaptor.capture());
      log.info("user email;->{}",user.getEmail());

      platformUser.deleteUser(platuser.getEmail());
      log.info("user email;->{}",platuser.getEmail());
    }

    @Test
    void addFollower() {
    }

    @Test
    void addFollowing() {
    }

    @Test
    void findForumUser() {
    }

    @Test
    void unfollowUser() {
    }

    @Test
    void deleteAllUser() {
    }

    @Test
    void testFindForumUser() {
    }
}