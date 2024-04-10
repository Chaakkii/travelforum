package hh.sof03.travelexp.web;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.CategoryRepository;
import hh.sof03.travelexp.domain.ForumThread;


@Controller

public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;



    @GetMapping(value = "/etusivu")
    public String loadFirstTitle(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

            List<String> authorities = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());


        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("authorities", authorities);

        model.addAttribute("categories", categoryRepository.findAll());

        return "etusivu"; // etusivu.html
    }

    @GetMapping(value = "/category/{categoryId}")
    public String findByCategoryid(@PathVariable("categoryId") Long categoryId, Model model) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            Category selectedCategory = category.get();
            
            List<ForumThread> threads = selectedCategory.getThreads();
    
            Collections.sort(threads, new Comparator<ForumThread>() {
                @Override
                public int compare(ForumThread t1, ForumThread t2) {
                    return t2.getStartDay().compareTo(t1.getStartDay());
                }
            });

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        List<String> authorities = authentication.getAuthorities()
                               .stream()
                               .map(GrantedAuthority::getAuthority)
                               .collect(Collectors.toList());


        
        model.addAttribute("loggedInUsername", loggedInUsername);
        model.addAttribute("authorities", authorities);
        model.addAttribute("category", category.orElse(null));
    
        
    }       
        return "threads";
}

    @GetMapping(value="/addcategory")
    public String addCategory(Model model) {
        model.addAttribute("category", new Category());

        return "newcategory";
    }
    
    @PostMapping(value ="/addcategory")
    public String addCategoryPost(@ModelAttribute("category") Category category, Authentication authentication, Model model, @RequestParam("name") String name, @RequestParam("description") String description) {
       
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {

        model.addAttribute("errorMessage", "Forbidden.");
        return "error";
    } else {
        Category cat = new Category();

        cat.setName(name);
        cat.setDescription(description);

        categoryRepository.save(cat);

        return"redirect:/etusivu";
    }
    }

    @GetMapping(value = "/deletecategory/{categoryid}")
    public String deleteCategory(@PathVariable("categoryid") Long categoryId, Authentication authentication, Model model){

    if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {

        model.addAttribute("errorMessage", "Forbidden.");
        return "error";
    } else {

        categoryRepository.deleteById(categoryId);
    }

    return"redirect:/etusivu";



}
}