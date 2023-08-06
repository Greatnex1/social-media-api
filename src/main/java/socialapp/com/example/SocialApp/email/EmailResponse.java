package socialapp.com.example.SocialApp.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Validated
public class EmailResponse {
    @NotEmpty
    private String message;

    private Boolean status;
}
