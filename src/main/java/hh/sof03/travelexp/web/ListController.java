package hh.sof03.travelexp.web;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.sof03.travelexp.domain.Category;
import hh.sof03.travelexp.domain.Thread;
import org.springframework.web.bind.annotation.RequestParam;



@Controller

public class ListController {

    @GetMapping(value ="/forum")
    public String loadFrontPage(Model model) {

        List<Thread> threads = new ArrayList<Thread>();

        LocalDate startDay = LocalDate.now();

        model.addAttribute("threads", threads);

        threads.add(new Thread("Ranskan matka", startDay, 10));
        threads.add(new Thread("Kiinan matka", startDay, 15));
        threads.add(new Thread("Hollannin matka", startDay, 12));


        return "home"; //home.html
    }
    @GetMapping(value = "/matka")
    public String loadFirstTitle(Model model) {
        List <Category> categories = new ArrayList<Category>();

        model.addAttribute("categories", categories);

        categories.add(new Category("Matka"));
        categories.add(new Category("EM2024"));
        categories.add(new Category("Seurajoukkuejalkapallo"));



        return "matkakokemukset"; //matkakokemukset.html
    }
    
    

}
