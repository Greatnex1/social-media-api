package socialapp.com.example.SocialApp.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Properties;

@Component
@Setter
@Getter
public class EmailConfig {

    @NotNull
    @Value("${spring.mail.host}")
    private String host;

    @NotNull
    @Value("${spring.mail.port}")
    private int port;

    @NotNull
    @Value("${spring.mail.username}")
    private String username;

    @NotNull
    @Value("${spring.mail.password}")
    private String password;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(getHost());
        mailSender.setPort(getPort());
        mailSender.setUsername(getUsername());
        mailSender.setPassword(getPassword());

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
        mailSender.setJavaMailProperties(javaMailProperties);

        return mailSender;
    }
}

