package socialapp.com.example.SocialApp.dtos;


import lombok.Data;

@Data
public class CommentDto {

    private String commenter;

    private String content;

    private Long postId;
}
