package hh.sof03.travelexp.web;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@Controller

public class ForumThreadController {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageRepository messageRepository;


    @GetMapping(value ="/threads")
    public String loadFrontPage(Model model) {

        List<ForumThread> threads = (List<ForumThread>) threadRepository.findAll();

        Collections.sort(threads, new Comparator<ForumThread>() {
        @Override
        public int compare(ForumThread t1, ForumThread t2) {
            return t2.getStartDay().compareTo(t1.getStartDay());
        }
    });

    model.addAttribute("threads", threads);

        return "home"; //home.html
    }

    @GetMapping("/thread/{id}/comments")
    public String showComments(@PathVariable("id") Long id, Model model) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null) {
            List<Message> comments = thread.getMessages(); // Olettaen, että viestit tallennetaan ForumThread-oliolle
            
        
            
            model.addAttribute("thread", thread);
            model.addAttribute("comments", comments);
            
            return "threadcomments";
    }
    return "error";
}


    @GetMapping(value="/addthread")
    public String newThread(Model model) {
        model.addAttribute("thread", new Thread());
        model.addAttribute("categories", categoryRepository.findAll());
        return "addthread";
    }

    @PostMapping("/add")
    public String addNewThread(@ModelAttribute("thread") ForumThread forumThread) {
       
        threadRepository.save(forumThread);

        return "redirect:/threads";
    }

    @GetMapping("/delete/{id}")
    public String deleteThread(@PathVariable Long id, Model model) {
        threadRepository.deleteById(id);
        return "redirect:/threads";
    }
    
    
    
    

}



