package dev.ppakst.grayAI.utils;

import dev.ppakst.grayAI.dto.MessageDTO;

public class MessageUtil {
    private MessageUtil(){ }

    public static String makeQuestionByTitleAndDescription(MessageDTO.Request messageRequest)
    {
        return String.format("%s : %s", messageRequest.getTitle(), messageRequest.getDescription());
    }

}
