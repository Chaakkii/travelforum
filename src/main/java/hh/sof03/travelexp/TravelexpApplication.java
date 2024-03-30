package hh.sof03.travelexp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ThreadRepository;
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
	public CommandLineRunner demo(CategoryRepository categoryRepository, ThreadRepository threadRepository, MessageRepository messageRepository) {
		return (args) -> {
			log.info("Few cateogry");
			Category cat1 = new Category("Menneet matkat", "Menneiden matkojen muistolle");
			Category cat2 = new Category("MM2024", "Kesäks kisoihi?");
			Category cat3 = new Category("Seurajoukkue matkat", "Valioliiga, LaLiga, Bundesliiga, Serie A tai Ligue 1?");

			categoryRepository.save(cat1);
			categoryRepository.save(cat2);
			categoryRepository.save(cat3);
			
			List<Category> categories = new ArrayList<>();

			categories.add(cat1);
			categories.add(cat2);
			categories.add(cat3);
			

			List<ForumThread> threads = new ArrayList<ForumThread>();

       		LocalDateTime startDay = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			String formattedDateTime = startDay.format(formatter);



        	threads.add(new ForumThread("Ranskan matka", startDay, cat1));
       		threads.add(new ForumThread("Kiinan matka", startDay, cat2));
        	threads.add(new ForumThread("Hollannin matka", startDay, cat3));

			threadRepository.saveAll(threads);

			log.info("Fetch all threads");
			for (ForumThread thread : threadRepository.findAll()) {
				log.info(thread.toString());
			}
			
			log.info("Fetch all categories");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}


			

		};
	}

}
