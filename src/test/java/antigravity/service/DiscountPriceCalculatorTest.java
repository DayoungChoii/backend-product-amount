package antigravity.service;

import antigravity.constant.PromotionType;
import antigravity.domain.entity.Promotion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class DiscountPriceCalculatorTest {

    @Test
    void calculateTest() {
        //given
        int originPrice = 215_000;
        Promotion couponPromotion = Promotion.builder()
                .promotion_type(PromotionType.COUPON)
                .discount_value(30_000)
                .build();
        Promotion codePromotion = Promotion.builder()
                .promotion_type(PromotionType.CODE)
                .discount_value(15)
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