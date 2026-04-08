package dev.ppakst.grayAI.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import com.google.auto.value.AutoValue.Builder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class MessageDTO {
    
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    @ToString
    public static class Request {
        @Schema(description = "메세지 제목", example = "제품 문의")
        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @Schema(description = "메세지 내용", example = "블랙 색상은 언제 입고되나요?")
        @NotBlank(message = "내용은 필수입니다.")
        private String description;

        @Schema(description = "사용자", example = "Park")
        @NotBlank(message = "사용자 정보는 필수입니다.")
        private String userName;
    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long no;
        private String title;
        private String description;
        private String userName;
        private LocalDateTime createdAt;
    }
}
