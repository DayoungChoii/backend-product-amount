package antigravity.exception;

import antigravity.common.CustomException;
import antigravity.constant.ExceptionCode;

public class ProductAmountException extends CustomException {
    public ProductAmountException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
