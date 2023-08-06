package socialapp.com.example.SocialApp.dtos;

import lombok.Data;

@Data
public class LoginResponseObj {

    private String accessToken;

    private String refreshToken;

    private Object user;
}
