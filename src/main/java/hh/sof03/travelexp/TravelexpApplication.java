package hh.sof03.travelexp;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import hh.sof03.travelexp.domain.ForumThread;

@SpringBootApplication
public class TravelexpApplication {
	private static final Logger log = LoggerFactory.getLogger(TravelexpApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TravelexpApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CategoryRepository categoryRepository, ThreadRepository threadRepository) {
		return (args) -> {
			log.info("Few cateogry");
			Category cat1 = new Category("Menneet matkat");
			Category cat2 = new Category("MM2024");
			Category cat3 = new Category("Seurajoukkue matkat");

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



        	threads.add(new ForumThread("Ranskan matka", startDay, 10, cat1));
       		threads.add(new ForumThread("Kiinan matka", startDay, 15, cat2));
        	threads.add(new ForumThread("Hollannin matka", startDay, 12, cat3));

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
