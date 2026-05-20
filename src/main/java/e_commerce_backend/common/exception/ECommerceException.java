package e_commerce_backend.common.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ECommerceException extends RuntimeException {
    private final ErrorCode errorCode;
}
