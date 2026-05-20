package e_commerce_backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    AUTH_01(101, "Error"),
    AUTH_03(103, "Chưa gửi Otp !!!"),
    AUTH_04(104, "Otp invalid !!!"),
    AUTH_05(105, "Double not allowed password old !!"),
    AUTH_02(102, "Bạn đã yêu cầu gửi otp rồi !!! Vui lòng đợi "),

    PRODUCT(01, "Nhập thuộc tính sai !!"),
    PRODUCT01(02, "Nhập thuộc bị trùng !!");

    private final int code;
    private final String message;

}
