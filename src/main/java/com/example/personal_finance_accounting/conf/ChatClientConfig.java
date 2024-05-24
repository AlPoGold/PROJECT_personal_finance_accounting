package com.example.personal_finance_accounting.conf;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient() {

        return new ChatClient() {
            @Override
            public String call(String message) {
                return ChatClient.super.call(message);
            }

            @Override
            public ChatResponse call(Prompt prompt) {
               return null;
            }
        };
    }

    @Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
}
