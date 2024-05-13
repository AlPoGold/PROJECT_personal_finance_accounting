package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.User;
import com.example.personal_finance_accounting.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.logging.Level;

@Controller
@AllArgsConstructor
@Log
public class WebController {
    @Autowired
    UserService userService;

//    @Autowired
//    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @PostMapping("/login")
//    public String loginSubmit() {
//        // Можно добавить логику для обработки успешной аутентификации
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        // Ваш код для обработки успешной аутентификации
//        return "redirect:/"; // Перенаправляем пользователя на главную страницу
//    }




    @GetMapping("/registration")
    public String registrationRequest(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String registrationRequest(@ModelAttribute("user") User user, Model model){

        userService.saveUser(user);
        log.log(Level.INFO, "user was added!" + user.getEmail()+ user.getPassword());
        return "redirect:/login";
    }
}
