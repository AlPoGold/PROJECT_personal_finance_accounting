package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.User;
import com.example.personal_finance_accounting.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor

public class WebController {
    UserService userService;

    @GetMapping("/login")
    public String loginRequest(){
        return "login";
    }


    @GetMapping("/registration")
    public String registrationRequest(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registration")
    public String registrationRequest(@ModelAttribute("user") User user, Model model){
        userService.saveUser(user);
        return "redirect:/login";
    }
}
