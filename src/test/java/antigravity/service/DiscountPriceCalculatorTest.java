package antigravity.service;

import antigravity.constant.PromotionType;
import antigravity.domain.entity.Promotion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DiscountPriceCalculatorTest {

    private final int ORIGIN_PRICE = 215_000;
    private final int COUPON_DISCOUNT_VALUE = 30_000;
    private final int CODE_DISCOUNT_VALUE = 15;

    @Test
    void calculateTest() {
        //given
        int originPrice = ORIGIN_PRICE;
        Promotion couponPromotion = Promotion.builder()
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .build();
        Promotion codePromotion = Promotion.builder()
                .promotion_type(PromotionType.CODE)
                .discount_value(CODE_DISCOUNT_VALUE)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);
        promotions.add(codePromotion);

        //when
        DiscountPriceCalculator discountPriceCalculator = new DiscountPriceCalculator();
        int discountPrice = discountPriceCalculator.calculate(originPrice, promotions);

        //then
        Assertions.assertThat(discountPrice).isEqualTo(30_000 + 32_250);
    }

}