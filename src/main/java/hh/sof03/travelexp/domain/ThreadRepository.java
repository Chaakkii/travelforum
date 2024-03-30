package hh.sof03.travelexp.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ThreadRepository extends CrudRepository<ForumThread, Long> {

    List<ForumThread> findByTitle(String title);
    


}
