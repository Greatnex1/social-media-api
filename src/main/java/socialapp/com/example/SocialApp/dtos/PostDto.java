package socialapp.com.example.SocialApp.dtos;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Validated
public class PostDto {
    @NotEmpty(message = "Content cannot be null")
    private String content;

    private Set<String> imageFile;

    private String videoUrl;


    private String userName;

    private Long postId;
}
