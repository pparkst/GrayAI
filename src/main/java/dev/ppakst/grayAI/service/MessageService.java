package dev.ppakst.grayAI.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.ppakst.grayAI.domain.Message;
import dev.ppakst.grayAI.dto.MessageDTO;
import dev.ppakst.grayAI.mapper.MessageMapper;
import dev.ppakst.grayAI.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Transactional
    public Long saveMessage(MessageDTO.Request messageRequest) 
    {
        Message message = messageMapper.toEntity(messageRequest);

        System.out.println(message.toString());
        message = messageRepository.save(message);

        return message.getId();
    }

    @Transactional(readOnly = true)
    public List<MessageDTO.Response> getAllMessageList() 
    {
        List<Message> messageList = messageRepository.findAll();

        return messageList.stream().map(messageMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public MessageDTO.Response getMessageByNo(Long messageNo)
    {
        Message message = messageRepository.findById(messageNo).orElseThrow(() -> new NoSuchElementException("대상이 없습니다."));
        return messageMapper.toResponse(message);
    }

}
