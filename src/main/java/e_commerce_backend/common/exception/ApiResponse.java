package e_commerce_backend.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    private int code = 200;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(200, message, null);
    }
    public static <T> ApiResponse<T> successCustom(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    public static <T> ApiResponse<T> successData(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    public static <T> ApiResponse<T> errorException(int code, String message, T data) {
        return new ApiResponse<>(code, message, null);
    }



}
