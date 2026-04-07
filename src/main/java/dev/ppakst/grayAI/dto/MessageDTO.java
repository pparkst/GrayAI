package dev.ppakst.grayAI.dto;

import java.time.LocalDateTime;

import com.google.auto.value.AutoValue.Builder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class MessageDTO {
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @ToString
    public static class Request {
        @Schema(description = "메세지 제목", example = "제품 문의")
        private String title;

        @Schema(description = "메세지 내용", example = "블랙 색상은 언제 입고되나요?")
        private String description;

        @Schema(description = "사용자", example = "Park")
        private String userName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @ToString
    public static class Response {
        private Long No;
        private String title;
        private String description;
        private String userName;
        private LocalDateTime createdAt;
    }
}
