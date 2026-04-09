package dev.ppakst.grayAI.controller;

import java.io.IOException;
import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.ppakst.grayAI.dto.MessageDTO;
import dev.ppakst.grayAI.dto.MessageDTO.Response;
import dev.ppakst.grayAI.service.GeminiService;
import dev.ppakst.grayAI.service.MessageService;
import dev.ppakst.grayAI.utils.MessageUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/message")
public class MessageController {
    private MessageService messageService;
    private GeminiService geminiService;

    @PostMapping()
    public ResponseEntity<?> sendMessage(@Valid @RequestBody MessageDTO.Request messageRequest) 
    {
        String question = MessageUtil.makeQuestionByTitleAndDescription(messageRequest);

        String answer = geminiService.getAnswer(question);

        messageService.saveMessage(messageRequest);

        return ResponseEntity.ok(answer);
    }


    @PostMapping("/image")
    public ResponseEntity<?> sendMessageWithImage(@Valid @RequestBody MessageDTO.Request messageRequest, MultipartFile file) throws IOException 
    {
        String question = MessageUtil.makeQuestionByTitleAndDescription(messageRequest);

        String answer = geminiService.analyzeImage(question, new InputStreamResource(file.getInputStream()));

        //TODO: 파일 저장 필요

        messageService.saveMessage(messageRequest);

        return ResponseEntity.ok(answer);
    }
    

    @PostMapping("/add")
    public ResponseEntity<?> addMessage(@Valid @RequestBody MessageDTO.Request messageRequest) 
    {
        Long messageNo = messageService.saveMessage(messageRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(messageNo)
                .toUri();

        return ResponseEntity.created(location).body(messageNo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMessage(@PathVariable("id") Long messageNo) {
        return ResponseEntity.ok(messageService.getMessageByNo(messageNo));
    }
    
}
