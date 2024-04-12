package hh.sof03.travelexp.web;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;
import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register") 
        public String newUser(Model model) {
            model.addAttribute("user", new User());


            return "newuserform";
        }

    @PostMapping("/register")
    public String addNewUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        User existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser != null) {
            bindingResult.rejectValue("username", "error.user", "Käyttäjänimi on jo käytössä.");
        }

        User existingEmail = userRepository.findByEmail(user.getEmail());

        if (existingEmail != null) {
            bindingResult.rejectValue("email", "error.email", "Sähköpostiosoite on jo käytössä.");
        }
       
    
        if (bindingResult.hasErrors()) {

             return "newuserform";
        } else {

        redirectAttributes.addFlashAttribute("success", true);
    
        String encodedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(encodedPassword);
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login";

    }
}

@GetMapping("/userinfo")
public String userProfile(Model model, Authentication authentication) {
    if (authentication != null && authentication.isAuthenticated()) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("username", username);
            List<Message> userMessages = messageRepository.findByUser(user);
            model.addAttribute("messages", userMessages);
            return "userinfo"; 
        }
    }
    
    model.addAttribute("errorMessage", "Tietoja ei löytynyt.");
    return "error";
}

}
