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

import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.ThreadRepository;


@CrossOrigin
@Controller
public class ForumThreadRestController {

    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping(value = "forumthreads")
    public @ResponseBody List<ForumThread> getForumThreadRest() {
        return (List<ForumThread>) threadRepository.findAll();
    }

    @GetMapping(value = "forumthreads/{id}")
    public @ResponseBody Optional<ForumThread> findForumThreadRest(@PathVariable("id") Long id) {
        return threadRepository.findById(id);
    }

    @PostMapping(value = "forumthreads")
    public @ResponseBody ForumThread saveThread(@RequestBody ForumThread forumThread) {
        return threadRepository.save(forumThread);
    }

}
