package dev.ppakst.grayAI.controller;

import java.net.URI;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.ppakst.grayAI.dto.MessageDTO;
import dev.ppakst.grayAI.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@AllArgsConstructor
@RestController()
@RequestMapping("/api/v1/message")
public class MessageController {
    private MessageService messageService;

    @PostMapping()
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
