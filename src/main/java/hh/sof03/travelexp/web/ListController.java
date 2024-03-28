package hh.sof03.travelexp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class ListController {

    @GetMapping(value ="/travel")
    public String loadFrontPage() {
        return "home"; //home.html
    }
    

}
