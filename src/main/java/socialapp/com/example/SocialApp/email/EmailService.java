package socialapp.com.example.SocialApp.email;

import socialapp.com.example.SocialApp.dtos.UserDto;

public interface EmailService {
    public void sendWelcomeMessage(UserDto userDto, String token);

    void newTokenMail(String email, String newToken);

}
