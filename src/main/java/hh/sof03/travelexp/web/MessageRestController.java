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

import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;


@CrossOrigin
@Controller
public class MessageRestController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(value = "/messages")
    public @ResponseBody List<Message> getMessagesRest() {
        return (List<Message>) messageRepository.findAll();
    }

    @GetMapping(value = "/messages/{id}")
    public @ResponseBody Optional<Message> getMessageRest(@PathVariable("id") Long messageId) {
        return messageRepository.findById(messageId);
    }

    @PostMapping(value = "/messages") 
    public @ResponseBody Message saveMessageRest(@RequestBody Message message) {
        return messageRepository.save(message);
    }

}
