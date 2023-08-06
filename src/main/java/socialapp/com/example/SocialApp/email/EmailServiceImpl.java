package socialapp.com.example.SocialApp.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateEngineException;
import socialapp.com.example.SocialApp.dtos.UserDto;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;


    @Autowired
    SocialMediaUserRepo socialMediaUserRepo;

    private PasswordEncoder passwordEncoder;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Override
    public void sendWelcomeMessage(UserDto request, String token) {
        String link = baseUrl + "/v1/user/verify?token=" +token;
        Context ctx = new Context(Locale.getDefault());
        ctx.setVariable("req", request.getFirstname());
        ctx.setVariable("link", link);
        String html = templateEngine.process("welcome", ctx);
       getEmailResponse(request, html);
    }

    @Override
    public void newTokenMail(String email, String newToken) {
        String link = baseUrl + "/v1/user/verify?token=" + newToken;
        final SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setSubject("New Verification Link!");
        mailMessage.setFrom("info@funage.com");
        mailMessage.setText(
                "Please click on the below link to verify your account. Link expires in 24 hours" + "\n" + link);
        mailSender.send(mailMessage);
    }

    private void getEmailResponse(UserDto request, String html) {
        EmailResponse response = new EmailResponse();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom("info@funage.com");
            helper.setText(html, true);
            helper.setTo(request.getEmail());
            helper.setSubject("Welcome Onboard");

            mailSender.send(message);

            response.setMessage("mail send to: "+ request.getEmail());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException | TemplateEngineException e) {
            response.setMessage("Mail sending failure : " + e.getMessage());
            response.setStatus(Boolean.FALSE);

        }

    }
}
