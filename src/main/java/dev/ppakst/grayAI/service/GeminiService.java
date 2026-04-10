package dev.ppakst.grayAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import io.lettuce.core.RedisConnectionException;
import reactor.core.publisher.Flux;

@Service
public class GeminiService {

    private final ChatClient chatClient;
    private final StringRedisTemplate redisTemplate;

    public GeminiService(ChatClient.Builder builder, StringRedisTemplate redisTemplate)
    {
        this.chatClient = builder.build();
        this.redisTemplate = redisTemplate;
    }

    public Flux<String> getStreamingAnswer(String message)
    {
        String cacheKey = String.format("grayAi:cache:%s", message);

        try{
            
            String cachedAnswer = redisTemplate.opsForValue().get(cacheKey);
    
            if(cachedAnswer != null)
            {
                System.out.println("Redis 가져옴");
                return Flux.just(cachedAnswer);
            }
        }catch(RedisConnectionFailureException | RedisConnectionException e)
        {
            System.err.println("Redis 연결 실패 001");
            return callGeminiApiAndSaveCache(message);
        }catch(Exception e)
        { 
            System.err.println("Redis 오류 발생 001");
            return callGeminiApiAndSaveCache(message);
        }

        return callGeminiApiAndSaveCache(message);
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

    private Flux<String> callGeminiApiAndSaveCache(String message)
    {
        String cacheKey = String.format("grayAi:cache:%s", message);

        StringBuilder fullAnswer = new StringBuilder();

        return chatClient.prompt()
                .user(message)
                .stream() //스트리밍 활성화
                .content() //내용만 추출하여 Flux로 변환
                .doOnNext(fullAnswer::append)
                .doOnComplete(() -> {
                    try{
                        redisTemplate.opsForValue().set(cacheKey, fullAnswer.toString());
                        System.out.println("Redis 저장 완료");
                    }catch (Exception e) {
                        System.err.println("Redis 저장 실패: " + e.getMessage());
                    }
                })
                .onErrorResume(e -> {
                    return Flux.just("\n\n⚠️ 현재 서비스 이용이 원활하지 않습니다. 잠시 후 다시 시도해 주세요.");
                });
    }
}
