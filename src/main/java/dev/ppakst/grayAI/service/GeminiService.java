package dev.ppakst.grayAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class GeminiService {
    private final ChatClient chatClient;
    
    public GeminiService(ChatClient.Builder builder) 
    {
        this.chatClient = builder.build();
    }

    public String getAnswer(String message)
    {
        return chatClient.prompt(message)
                .call()
                .content();
    }

    public String analyzeImage(String message, Resource imgeResource)
    {
        return chatClient.prompt()
                .user(u -> u
                    .text(message)
                    .media(MimeTypeUtils.IMAGE_JPEG, imgeResource)
                )
                .call()
                .content();
    }
}
