package com.example.personal_finance_accounting.service;

import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String API_KEY;

    @Value("${MODEL_ID}")
    private String MODEL_ID;

    @Value("${URL}")
    private String URL;

    public String OpenAiServiceCall(String message) {
        String url = URL;

        // Заголовки запроса
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.set("Content-Type", "application/json");

        // Тело запроса
        String requestBody = "{"
                + "\"model\": \"" + MODEL_ID + "\", "
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}], "
                + "\"max_tokens\": 4000"
                + "}";

        // Создание HttpEntity, включающего заголовки и тело запроса
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Отправка POST-запроса
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return parseResponse(response.getBody());
        } catch (HttpClientErrorException.TooManyRequests e) {
            // Обработка ошибки "Too Many Requests"
            System.out.println("Too many requests: " + e.getMessage());
            return "You have exceeded your API quota. Please try again later.";
        } catch (HttpClientErrorException e) {
            // Обработка других ошибок HTTP
            System.err.println("Http error: " + e.getMessage());
            return "An error occurred while calling the API.";
        } catch (Exception e) {
            // Обработка других возможных ошибок
            System.err.println("Error: " + e.getMessage());
            return "An unexpected error occurred.";
        }
    }

    private String parseResponse(String jsonResponse) {
        try {
            String content = JsonPath.read(jsonResponse, "$.choices[0].message.content");
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unable to get a valid response from the API.";
    }
}
