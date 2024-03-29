package ua.vitalii.bella.deep_translator_ai.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ua.vitalii.bella.deep_translator_ai.model.gpt.common.Message;
import ua.vitalii.bella.deep_translator_ai.model.gpt.request.ChatGPTRequest;
import ua.vitalii.bella.deep_translator_ai.model.gpt.response.ChatGPTResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPEN_AI_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    public ChatGPTResponse getChatGPTResponse(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setModel("gpt-3.5-turbo");
        chatGPTRequest.setMessages(List.of(new Message("user", prompt)));
        chatGPTRequest.setMax_tokens(300);

        HttpEntity<ChatGPTRequest> request = new HttpEntity<>(chatGPTRequest, headers);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForObject(OPEN_AI_CHAT_ENDPOINT, request, ChatGPTResponse.class);
    }
}