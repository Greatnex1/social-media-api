package socialapp.com.example.SocialApp.exception;

public class PostAlreadyExistException extends RuntimeException {
    public PostAlreadyExistException(String message) {
        super(message);
    }
}
