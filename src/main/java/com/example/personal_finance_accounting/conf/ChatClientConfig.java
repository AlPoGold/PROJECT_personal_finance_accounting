package com.example.personal_finance_accounting.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ChatClientConfig {

    @Bean
    public RestTemplate getRestTemplate() {

        return new RestTemplate();
    }
}
