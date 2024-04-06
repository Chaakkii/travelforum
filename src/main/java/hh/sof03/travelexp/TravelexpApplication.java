package hh.sof03.travelexp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
import hh.sof03.travelexp.domain.User;
import hh.sof03.travelexp.domain.UserRepository;
import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.Message;
import hh.sof03.travelexp.domain.MessageRepository;

@SpringBootApplication
public class TravelexpApplication {
	private static final Logger log = LoggerFactory.getLogger(TravelexpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TravelexpApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository, ThreadRepository threadRepository, MessageRepository messageRepository, UserRepository userRepository) {
		return (args) -> {
			log.info("Few cateogry");
			Category cat1 = new Category("Menneet matkat", "Menneiden matkojen muistolle");
			Category cat2 = new Category("EM2024", "Jalkapallon euroopan mestaruuskisat 2024 Saksassa");
			Category cat3 = new Category("Seurajoukkuematkat", "Valioliiga, LaLiga, Bundesliiga, Serie A tai Ligue 1?");

			categoryRepository.save(cat1);
			categoryRepository.save(cat2);
			categoryRepository.save(cat3);
			
			List<Category> categories = new ArrayList<>();

			categories.add(cat1);
			categories.add(cat2);
			categories.add(cat3);
			
			List<ForumThread> threads = new ArrayList<ForumThread>();

       		LocalDateTime startDay = LocalDateTime.now();

        	threads.add(new ForumThread("Saksan matka", startDay, cat1));
       		threads.add(new ForumThread("Kisamatka Saksaan?", startDay, cat2));
        	threads.add(new ForumThread("Seurajoukkuejalkapalloreissu?", startDay, cat3));

			threadRepository.saveAll(threads);

			User user1 = new User("username123", "user", "user@user.com", "user");

			userRepository.save(user1);



			ForumThread thread1 = threadRepository.findById(1L).orElse(null);
			if (thread1 != null) {
				threadRepository.save(thread1);
				Message comment1 = new Message("Hyvä matka oli tuo reissu Berliinii, upee kaupunki!", thread1, user1);
				messageRepository.save(comment1);
			}
	
			ForumThread thread2 = threadRepository.findById(2L).orElse(null);
			if (thread2 != null) {
				threadRepository.save(thread2);
				Message comment2 = new Message("Mitähä siellä saksassa tekiskään, ku jalkapalloo kattoo kesällä jos sais lippuja. Miten ois?", thread2, user1);
				messageRepository.save(comment2);
			}
	
			ForumThread thread3 = threadRepository.findById(3L).orElse(null);
			if (thread3 != null) {
				threadRepository.save(thread3);
				Message comment3 = new Message("Pitäskö tossa syksyllä käyä joku reissu tekee kattoo hyvää palloo tonne eurooppaan, esimerkiks vaik espanjaan?", thread3, user1);
				messageRepository.save(comment3);
			}

			log.info("Fetch all threads");
			for (ForumThread thread : threadRepository.findAll()) {
				log.info(thread.toString());
			}
			
			log.info("Fetch all categories");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}

			log.info("Fetch all messages");
			for (Message message : messageRepository.findAll()) {
				log.info(message.toString());
			}


			

		};
	}

}
