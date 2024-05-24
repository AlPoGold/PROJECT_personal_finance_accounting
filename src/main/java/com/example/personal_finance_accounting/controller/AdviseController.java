package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.service.OpenAIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Log
@RequestMapping("/advices")
public class AdviseController {

    @Autowired
    private final OpenAIService openAiService;


    @GetMapping
    public String showAdvicesPage() {
        return "advices";
    }

    @PostMapping
    public String handleUserMessage(@RequestParam("userMessage") String userMessage, Model model) {
        String response = openAiService.OpenAiServiceCall(userMessage);
        model.addAttribute("response", response);
        return "advices";
    }

}
