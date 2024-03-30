package hh.sof03.travelexp.web;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import org.springframework.web.bind.annotation.RequestParam;





@Controller

public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

   
    @GetMapping(value = "/matka")
    public String loadFirstTitle(Model model) {
       model.addAttribute("categories", categoryRepository.findAll() );


        return "matkakokemukset"; //matkakokemukset.html
    }
 
    @GetMapping(value = "/category/{categoryId}")
    public String findByCategoryid(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        

        
        model.addAttribute("category", category.orElse(null));
    
        return "threads";
    }
    

}
