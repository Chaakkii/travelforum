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

        model.addAttribute("category", category.orElse(null));
    
        return "threads";
    }
    
}