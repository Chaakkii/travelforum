package hh.sof03.travelexp.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping("/commentForm/{id}")
    public String getMethodName(@PathVariable("id") Long id, Model model) {

        model.addAttribute("id", id);
        return "commentForm";
    }

    @PostMapping("/saveComment")
    public String saveComment(@RequestParam("id") Long id, @RequestParam String content) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null) {
            Message message = new Message();
            message.setContent(content);
            message.setMessageTime(LocalDateTime.now());
            message.setForumThread(thread);
            messageRepository.save(message);
        }
        return "redirect:/thread/" + id + "/comments"; // Redirect to the thread comments page
    }

    @GetMapping("/comments/{id}")
    public String showComments(@PathVariable("id") Long id, Model model) {
        ForumThread thread = threadRepository.findById(id).orElse(null);
        if (thread != null) {
            List<Message> comments = thread.getMessages(); // Olettaen, että viestit tallennetaan ForumThread-oliolle

            // Muotoillaan viestien ajat
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
}
