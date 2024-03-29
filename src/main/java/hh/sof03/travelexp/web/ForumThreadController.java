package hh.sof03.travelexp.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ForumThread;
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


    @GetMapping(value ="/threads")
    public String loadFrontPage(Model model) {

        model.addAttribute("threads", threadRepository.findAll());


        return "home"; //home.html
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



