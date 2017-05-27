package com.bc.spring.thymeleaf.study.web.controller.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: banchun
 * Date:  2017-05-27
 * Time:  下午 2:35.
 * Description:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ThymeleafController {

    @RequestMapping("/hello")
    public String index(Model model){
        model.addAttribute("name","banchun");
        return "index";
    }

    @RequestMapping("/home")
    public String home(Model model){
        model.addAttribute("name","banchun");
        return "home";
    }
}
