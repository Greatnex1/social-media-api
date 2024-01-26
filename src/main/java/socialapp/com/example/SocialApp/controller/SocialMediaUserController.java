package socialapp.com.example.SocialApp.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.xml.messaging.saaj.soap.SOAPVersionMismatchException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import socialapp.com.example.SocialApp.dtos.LoginRequestDto;
import socialapp.com.example.SocialApp.dtos.LoginResponseObj;
import socialapp.com.example.SocialApp.dtos.UserDto;
import socialapp.com.example.SocialApp.dtos.UserProfileDto;
import socialapp.com.example.SocialApp.entity.AuthenticationType;
import socialapp.com.example.SocialApp.entity.Role;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.exception.SocialMediaUserException;
import socialapp.com.example.SocialApp.exception.UserAlreadyExistException;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;
import socialapp.com.example.SocialApp.service.SocialMediaUserServiceImpl;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class SocialMediaUserController {

    private final SocialMediaUserServiceImpl userService;

   private final AuthenticationManager authenticationManager;

    private final SocialMediaUserRepo userRepository;


    @PostMapping("/user")
    public ResponseEntity<?> userRegistration(@RequestBody @Valid UserDto userDto, HttpServletRequest req)  {
        try {
              return new ResponseEntity<>(userService.registerUser(userDto,req),HttpStatus.CREATED);
              //recheck
        } catch (DataIntegrityViolationException | UserAlreadyExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (com.sun.xml.messaging.saaj.packaging.mime.MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("users/{user_id}")
    public ResponseEntity<SocialMediaUser> findUser(@PathVariable Long user_id) {
        return new ResponseEntity<>(userService.findForumUser(user_id).get(), HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<List<SocialMediaUser>> getAllUsers() {
        return new ResponseEntity<>(userService.findAllUser(), HttpStatus.OK);
    }

    @PatchMapping("/user/profile-edit")
    public ResponseEntity<?> updateProfile(@RequestParam Long userId, @RequestBody UserProfileDto userProfileDto) {
        try {
            return new ResponseEntity<>(userService.updateProfile(userId, userProfileDto), HttpStatus.OK);
        } catch (SocialMediaUserException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/user/delete")
    public ResponseEntity<?> deleteUser(@RequestParam @NotEmpty String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted");
    }

    @DeleteMapping("/user/delete-all")
    public ResponseEntity<?> deleteAllUser() {
        userService.deleteAllUser();
        return ResponseEntity.ok("User deleted");
    }

   // @PostMapping("/role/addtouser")
 //   public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
 //       userService.addRoleToUser(form.getUsername(), form.getRoleName());
 //       return ResponseEntity.ok().build();
   // }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                SocialMediaUser user = userService.findForumUser(username);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_tokens", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch(Exception e){
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());


                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());

                response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);

            }

            }
        else{ throw new RuntimeException("Refresh token is missing");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @Valid LoginRequestDto loginDto, HttpServletRequest request,
                                              HttpServletResponse response){

        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail().toLowerCase(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = (User) authentication.getPrincipal();
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date( System.currentTimeMillis()+24*60*60*1000*3))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim("roles",
                            user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList()))
                    .sign(algorithm);
            String refresh_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 24 * 60 * 60 * 1000))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);
            response.setContentType(APPLICATION_JSON_VALUE);
       SocialMediaUser user1 = userRepository.findByUserNameOrEmail(user.getUsername(), user.getUsername())
               .orElseThrow(()-> new SocialMediaUserException("User details does not exist"));
       user1.setLastLogin(LocalDateTime.now().toString());
       user1.setAuthType(AuthenticationType.DATABASE);
       userRepository.save(user1);

            LoginResponseObj responseObj = new LoginResponseObj();
            responseObj.setAccessToken(access_token);
            responseObj.setRefreshToken(refresh_token);
            responseObj.setUser(user1);

            return ResponseEntity.ok(responseObj);
        }
catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
}
    }
    @GetMapping("/users/following/{user_id}")
    public ResponseEntity<?> findAllFriendsAmFollowing(@PathVariable("user_id") Long user_id) {

        return ResponseEntity.ok(userService.findAllFollowing(user_id));
    }

    @GetMapping("/users/followers/{user_id}")
    public ResponseEntity<?> findFollowers(@PathVariable("user_id") Long user_id) {

        return ResponseEntity.ok(userService.findAllFollowers(user_id));
    }

    @PostMapping("/user/manage-network/follower")
    public void addFollower(@RequestParam @NotBlank(message = "user cannot be blank") String user,
                            @RequestParam String follower) {
        userService.addFollower(user, follower);
    }

    @PostMapping("/user/manage-network/following")
    public void addFollowing(@RequestParam @NotBlank(message = "user cannot be blank") String user,
                             @RequestParam String following) {
        userService.addFollowing(user, following);
    }


}
