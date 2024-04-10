package hh.sof03.travelexp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
    }





