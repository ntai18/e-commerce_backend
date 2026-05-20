package e_commerce_backend.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(ECommerceException.class)
    public ResponseEntity<ApiResponse> handleECommerceException(ECommerceException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.ok().body(ApiResponse.errorException(errorCode.getCode(), errorCode.getMessage(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleException(MethodArgumentNotValidException e) {
        return ResponseEntity.ok().body(ApiResponse.successCustom(e.getStatusCode().value(), e.getBindingResult().getFieldError().getDefaultMessage()));
    }

}
