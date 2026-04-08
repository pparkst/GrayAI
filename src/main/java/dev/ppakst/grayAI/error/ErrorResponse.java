package dev.ppakst.grayAI.error;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private int errorCode;
    private String title;
    private String message;
    private List<FieldErrorDetail> errors;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FieldErrorDetail {
        private String field;
        private String value;
        private String reason;
    }

    public static ErrorResponse of(HttpStatus status, String message) {
        return ErrorResponse.builder()
                .errorCode(status.value())
                .title(status.getReasonPhrase())
                .message(message)
                .build();
    }
}
