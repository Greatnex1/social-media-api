package socialapp.com.example.SocialApp.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserProfileDto {
    private String userName;

    private String location;

    private String country;

    private String state;

    private LocalDate dob;
}
