package hh.sof03.travelexp.web;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ForumThread;
import hh.sof03.travelexp.domain.ThreadRepository;

import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ThreadRepository threadRepository;

    @GetMapping(value = "/matka")
    public String loadFirstTitle(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());

        return "matkakokemukset"; // matkakokemukset.html
    }

    @GetMapping(value = "/category/{categoryId}")
    public String findByCategoryid(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            Category foundCategory = category.get();
            List<ForumThread> threads = threadRepository.findByCategory(foundCategory);

            Collections.sort(threads, new Comparator<ForumThread>() {
                @Override
                public int compare(ForumThread t1, ForumThread t2) {
                    return t2.getStartDay().compareTo(t1.getStartDay());
                }
            });

            model.addAttribute("category", foundCategory);
            model.addAttribute("threads", threads);

            return "threads"; // threads.html -sivun näyttäminen
        } else {
            // Kategoriaa ei löytynyt
            return "error"; // error.html -sivun näyttäminen
        }
    }

}
