package hh.sof03.travelexp.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByUser(User user);

    List<Message> findByForumThreadId(Optional<ForumThread> threadOptional);

    Optional<Message> findByForumThread(ForumThread thread);


}
