package az.customers.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> {

    private boolean success;
    private String message;
    private T data;

    public static <T> CommonResponse<T> success(String message, T data) {
        return CommonResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return CommonResponse.<T>builder()
                .success(true)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> failed(String message, T data) {
        return CommonResponse.<T>builder()
                .success(false)
                .message(message)
                .data(data)
                .build();
    }

}
