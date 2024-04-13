package hh.sof03.travelexp.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;

@CrossOrigin
@Controller
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value ="/users")
    public @ResponseBody List<User> getUsersRest() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping(value="/users/{id}")
    public @ResponseBody Optional<User> getUserRest(@PathVariable("id") Long userId) {
        return userRepository.findById(userId);
    }

    @PostMapping(value = "/addusers")
    public @ResponseBody User saveUserRest(@RequestBody User user) {
        return userRepository.save(user);
    }
}
