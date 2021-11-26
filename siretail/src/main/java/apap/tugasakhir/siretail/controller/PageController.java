package apap.tugasakhir.siretail.controller;

import apap.tugasakhir.siretail.model.UserModel;
import apap.tugasakhir.siretail.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class PageController {
    @Autowired
    private UserService userService;
    
    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/")
    public String home(Model model){
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         User user = (User) auth.getPrincipal();
         String username = user.getUsername();
         UserModel userModel = userService.findByUsername(username);
         model.addAttribute("user", userModel);
         return "home";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}

