package hh.sof03.travelexp.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller

public class ForumThreadController {

    @Autowired
    private ThreadRepository threadRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/threads")
    public String loadFrontPage(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        List<ForumThread> threads = (List<ForumThread>) threadRepository.findAll();

        Collections.sort(threads, new Comparator<ForumThread>() {
            @Override
            public int compare(ForumThread t1, ForumThread t2) {
                return t2.getStartDay().compareTo(t1.getStartDay());
            }
        });

        model.addAttribute("authorities", authorities);
        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("threads", threads);

        return "allthreads";
    }

    @GetMapping("/thread/{id}/comments")
    public String showComments(@PathVariable("id") Long id, Model model) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null) {
            List<Message> comments = thread.getMessages();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUsername = authentication.getName();

            List<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            List<User> users = new ArrayList<>();
            for (Message comment : comments) {
                users.add(comment.getUser());
            }
            model.addAttribute("loggedInUsername", loggedInUsername);
            model.addAttribute("authorities", authorities);

            model.addAttribute("thread", thread);
            model.addAttribute("comments", comments);
            model.addAttribute("users", users);

            return "threadcomments";
        }
        model.addAttribute("errorMessage", "Jokin meni vikaan.");
        return "error";
    }

    @GetMapping(value = "/addthread")
    public String showAddThreadForm(@ModelAttribute("forumthread") ForumThread forumThread, Model model,
            @RequestParam(name = "categoryid") Long categoryId, HttpSession session) {
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("categoryId", categoryId);
        return "addthread";
    }

    @PostMapping("/addthread")
    public String addNewThread(@Valid @ModelAttribute("forumthread") ForumThread forumThread,
            BindingResult bindingResult, @RequestParam("title") String title,
            @RequestParam("content") String commentContent, @RequestParam("categoryId") Long categoryId,
            Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
        if (commentContent.length() < 1 || commentContent.length() > 3000) {
            bindingResult.rejectValue("messages", "error.content", "Ei tyhjiä viestejä. Korkeintaan 3000 merkkiä.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("forumthread", forumThread);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categories", categoryRepository.findAll());
            return "addthread";
        } else {
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByUsername(username);

                Message content = new Message();
                content.setContent(commentContent);
                content.setMessageTime(LocalDateTime.now());
                content.setUser(user);

                Message savedComment = messageRepository.save(content);

                Category category = categoryRepository.findById(categoryId).orElse(null);
                if (category != null) {
                    forumThread.setCategory(category);
                    forumThread.addMessage(savedComment);
                    threadRepository.save(forumThread);
                }
            }
            return "redirect:/threads";
        }
    }

    @GetMapping("/deletethread/{id}")
    public String deleteThread(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<ForumThread> threadOptional = threadRepository.findById(id);

        if (threadOptional.isPresent()) {
            ForumThread thread = threadOptional.get();

            List<Message> messages = messageRepository.findAllByForumThread(thread);

            Message firstMessage = messages.get(0);
         
                if (!firstMessage.getUser().getUsername().equals(loggedInUsername)
                        && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                    model.addAttribute("errorMessage", "Sinulla ei ole oikeuksia tähän toimintoon.");
                    return "error";
                
            }

            messageRepository.deleteAll(messages);

            threadRepository.deleteById(id);

            return "redirect:/etusivu";
        } else {
            model.addAttribute("errorMessage", "Odottamaton virhe.");
            return "error";
        }
    }

}
