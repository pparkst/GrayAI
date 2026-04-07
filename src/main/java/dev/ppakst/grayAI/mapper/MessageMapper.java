package dev.ppakst.grayAI.mapper;

import org.mapstruct.Mapper;

import dev.ppakst.grayAI.domain.Message;
import dev.ppakst.grayAI.dto.MessageDTO;
import dev.ppakst.grayAI.repository.MessageRepository;

@Mapper(componentModel = "spring", uses = {MessageRepository.class})
public interface MessageMapper {
    
    MessageDTO.Response toResponse(Message message);

    default Message toEntity(MessageDTO.Request messageRequest) {
        if (messageRequest == null) return null;

        return Message.builder()
                        .title(messageRequest.getTitle())
                        .description(messageRequest.getDescription())
                        .userName(messageRequest.getUserName())
                        .build();
    }
}
