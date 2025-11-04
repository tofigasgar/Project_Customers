package az.customers.model.response;

import az.customers.helper.GeneratorUUID;
import az.customers.model.enums.ErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonErrorResponse {

    private String message;
    private int errorCode;
    private LocalDateTime timestamp;
    private String uuid;
    private String path;

    public static CommonErrorResponse error(String message, ErrorCodes code, String path) {
        return CommonErrorResponse.builder()
                .message(message)
                .errorCode(code.getCode())
                .timestamp(LocalDateTime.now())
                .uuid(GeneratorUUID.generateUUID())
                .path(path)
                .build();
    }
}
