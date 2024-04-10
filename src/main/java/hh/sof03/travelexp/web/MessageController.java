package hh.sof03.travelexp.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/commentForm/{id}")
    public String showEditCommentForm(@PathVariable("id") Long id, Model model) {
        // Hae kommentti tietokannasta
        Message comment = messageRepository.findById(id).orElse(null);
        if (comment != null) {
            // Lisää kommentti malliin
            model.addAttribute("comment", comment);
            return "commentForm";
        } else {
            // Kommenttia ei löytynyt, ohjaa käyttäjä virhesivulle tai tee jotain muuta
            return "redirect:/error";
        }

    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<Message> commentOptional = messageRepository.findById(id);
        if (!commentOptional.isPresent()) {
            return "redirect:/error";
        }

        Message comment = commentOptional.get();

        if (!comment.getUser().getUsername().equals(loggedInUsername)
                && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            model.addAttribute("errorMessage", "Forbidden.");
            return "error";
        }

        model.addAttribute("comment", comment);
        return "edit";
    }

    @PostMapping("/saveComment") // tallentaa lähetetyn viestin
    public String saveComment(@RequestParam("id") Long id, @RequestParam String content,
            Authentication authentication) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null && authentication != null && authentication.isAuthenticated()) {

            String username = authentication.getName();
            User user = userRepository.findByUsername(username);

            Message message = new Message();
            message.setContent(content);
            message.setMessageTime(LocalDateTime.now());
            message.setUser(user);
            message.setForumThread(thread);
            messageRepository.save(message);
        }
        return "redirect:/thread/" + id + "/comments";
    }

    @PostMapping("/update/{id}")
    public String editComment(@PathVariable Long id, @RequestParam String content,
            @ModelAttribute("message") Message updatedMessage, Model model) {
        Message isMessage = messageRepository.findById(id).orElse(null);
        Long threadId = isMessage.getForumThread().getId();

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime createdTime = isMessage.getMessageTime();

        if (currentTime.minusMinutes(30).isAfter(createdTime)) {

            model.addAttribute("editend", true);

            return "redirect:/thread/" + threadId + "/comments?editend=true";

        }

        if (isMessage != null) {
            isMessage.setContent(updatedMessage.getContent());
            if (isMessage.getModifiedTime() == null) {
                isMessage.setModifiedTime(LocalDateTime.now());
            }

        messageRepository.save(isMessage);
        }

        if (isMessage.getModifiedTime() != null) {
            isMessage.setContent(updatedMessage.getContent());
            isMessage.setModifiedTime(LocalDateTime.now());

        }

        messageRepository.save(isMessage);
        return "redirect:/thread/" + threadId + "/comments";

      
    }


    @GetMapping("/comments/{id}") // ehkä turha, testimielessä silloin.
    public String showComments(@PathVariable("id") Long id, Model model) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null) {
            List<Message> comments = thread.getMessages();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            List<String> formattedMessageTimes = new ArrayList<>();
            for (Message comment : comments) {
                LocalDateTime messageTime = comment.getMessageTime();
                String formattedMessageTime = messageTime.format(formatter);
                formattedMessageTimes.add(formattedMessageTime);
            }

            model.addAttribute("thread", thread);
            model.addAttribute("comments", comments);
            model.addAttribute("formattedMessageTimes", formattedMessageTimes);
        }
        return "comments";
    }

    @GetMapping("/deletemessage/{id}")
    public String deleteComment(@PathVariable("id") Long messageId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        Optional<Message> messageOptional = messageRepository.findById(messageId);

        Long threadId = null;

        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            threadId = message.getForumThread().getId();

            if (!message.getUser().getUsername().equals(loggedInUsername)
                    && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
                model.addAttribute("errorMessage", "Forbidden.");

                return "error";
            }

            messageRepository.deleteById(messageId);

            Optional<ForumThread> threadOptional = threadRepository.findById(threadId);
            if (threadOptional.isPresent()) {
                ForumThread thread = threadOptional.get();
                List<Message> messagesInThread = thread.getMessages();
                if (messagesInThread.isEmpty()) {
                    threadRepository.deleteById(threadId);
                    return "redirect:/etusivu";
                }
            }
        }

        return "redirect:/thread/" + threadId + "/comments";
    }

    @GetMapping("/omatviestit")
    public String findUserMessages(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username);
            if (user != null) {
                List<Message> userMessages = messageRepository.findByUser(user);
                model.addAttribute("messages", userMessages);
                model.addAttribute("username", username);
                model.addAttribute("ForumThread", userMessages);
                model.addAttribute("id", userMessages);

            } else {
                model.addAttribute("messages", Collections.emptyList());
            }
        } else {
            model.addAttribute("messages", Collections.emptyList());
        }
        return "usermessages";
    }

}

