package antigravity.service;

import antigravity.exception.ProductAmountException;
import antigravity.model.ProductPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static antigravity.constant.ProductAmountExceptionCode.*;
import static org.junit.jupiter.api.Assertions.*;

class PriceValidatorTest {

    @Test
    void price_validate_test() {
        //given
        int originPrice = 10_000;
        int discountPrice = 20_000;

        //when
        PriceValidator priceValidator = new PriceValidator();

        ProductAmountException e = assertThrows(
                ProductAmountException.class,
                () -> priceValidator.validateAndGetFinalPrice(originPrice, discountPrice)
        );

        //then
        Assertions.assertThat(e.getExceptionCode()).isEqualTo(DISCOUNT_PRICE_OVERFLOW);

    }

    @Test
    void price_under_thousand_cut_when_final_price_is_over_thousand_test() {
        //given
        int originPrice = 10_000;
        int discountPrice = 100;

        //when
        PriceValidator priceValidator = new PriceValidator();
        ProductPrice productPrice = priceValidator.validateAndGetFinalPrice(originPrice, discountPrice);

        //then
        Assertions.assertThat(productPrice.getFinalPrice()).isEqualTo(9_000);
    }

    @Test
    void price_under_thousand_cut_when_final_price_is_under_thousand_test() {
        //given
        int originPrice = 1_000;
        int discountPrice = 100;

        //when
        PriceValidator priceValidator = new PriceValidator();
        ProductPrice productPrice = priceValidator.validateAndGetFinalPrice(originPrice, discountPrice);

        //then
        Assertions.assertThat(productPrice.getFinalPrice()).isEqualTo(900);
    }

}