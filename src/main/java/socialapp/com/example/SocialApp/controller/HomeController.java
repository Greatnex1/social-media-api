package socialapp.com.example.SocialApp.controller;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import socialapp.com.example.SocialApp.exception.SocialMediaUserException;
import socialapp.com.example.SocialApp.service.SocialMediaUserService;

import javax.transaction.InvalidTransactionException;
import javax.validation.constraints.NotBlank;

@Controller
@RequestMapping("v1")
public class HomeController {
    @Autowired
    SocialMediaUserService socialMediaUserService;

    @GetMapping("/user/verify")

    public String welcomeMessage(@RequestParam(value = "token") @NotBlank String token, Model model) {
        model.addAttribute("welcome", "Welcome to Fun Way");

       try{
           socialMediaUserService.verifyUser(token);
           model.addAttribute("welcome", "Welcome to Fun Way");
           return "success";
       }catch(MessagingException| SocialMediaUserException| InvalidTransactionException e){
           model.addAttribute("welcome", e.getMessage());

           return "failed";
       }
    }
}
