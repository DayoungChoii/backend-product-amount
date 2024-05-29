package antigravity.service;

import antigravity.exception.ProductAmountException;
import antigravity.model.ProductPrice;
import org.springframework.stereotype.Component;

import static antigravity.constant.ProductAmountExceptionCode.*;

@Component
public class PriceValidator {
    public ProductPrice validateAndGetFinalPrice(int originPrice, int discountPrice) {
        int finalPrice = originPrice - discountPrice;

        if(finalPrice < 0) {
            throw new ProductAmountException(DISCOUNT_PRICE_OVERFLOW);
        }

        if(finalPrice > 1000){
            finalPrice = finalPrice / 1000 * 1000;
        }

        return ProductPrice.builder()
                .originPrice(originPrice)
                .discountPrice(discountPrice)
                .finalPrice(finalPrice)
                .build();
    }
}
