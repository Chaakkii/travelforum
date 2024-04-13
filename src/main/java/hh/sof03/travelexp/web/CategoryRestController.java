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

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;

@CrossOrigin
@Controller
public class CategoryRestController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value ="/categories")
    public @ResponseBody List<Category> categoryListRest() {

        return (List<Category>) categoryRepository.findAll();
        
    }

    @GetMapping(value ="/categories/{id}")
    public @ResponseBody Optional<Category> findCategoryRest(@PathVariable("id") Long categoryId) {

        return categoryRepository.findById(categoryId);
    }

    @PostMapping(value ="/categories") // ei tietoa onnistuuko.
    public @ResponseBody Category saveCategoryRest(@RequestBody Category category) {
        
        return categoryRepository.save(category);
    }

}
