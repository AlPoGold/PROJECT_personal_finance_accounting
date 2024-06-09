package com.example.personal_finance_accounting.controller;

import com.example.personal_finance_accounting.model.UserAccount;
import com.example.personal_finance_accounting.service.OpenAIService;
import com.example.personal_finance_accounting.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private final UserAccountService userAccountService;


    @GetMapping
    public String showAdvicesPage(Model model, Authentication auth) {
        UserAccount user = userAccountService.findByEmail(auth.getName());
        Long userId = user.getUserId();
        model.addAttribute("userId", userId);
        return "advices";
    }


    /**
     * Получает совет от ИИ на основе пользовательского запроса.
     *
     * @param userMessage Сообщение от пользователя.
     * @return Ответ от ИИ.
     */
    @PostMapping
    public String handleUserMessage(@RequestParam("userMessage") String userMessage, Model model) {
        String response = openAiService.OpenAiServiceCall(userMessage);
        model.addAttribute("response", response);
        return "advices";
    }

}
