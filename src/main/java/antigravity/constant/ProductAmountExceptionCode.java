package antigravity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ProductAmountExceptionCode implements ExceptionCode {
    INVALID_PRODUCT(HttpStatus.BAD_REQUEST, "product does not exist"),
    INVALID_PROMOTION(HttpStatus.BAD_REQUEST, "promotion does not exist"),
    INVALID_USE_AT(HttpStatus.BAD_REQUEST, "started at or ended at is invalid"),
    OUT_OF_PRICE_RANGE(HttpStatus.INTERNAL_SERVER_ERROR, "price is out of range"),
    DISCOUNT_PRICE_OVERFLOW(HttpStatus.BAD_REQUEST, "total discount price is over origin price");

    private final HttpStatus httpStatus;
    private final String message;


}
