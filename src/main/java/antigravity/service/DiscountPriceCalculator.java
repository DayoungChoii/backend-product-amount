package antigravity.service;

import antigravity.domain.entity.Promotion;
import org.springframework.stereotype.Component;

import java.util.List;

import static antigravity.constant.PromotionType.CODE;
import static antigravity.constant.PromotionType.COUPON;

@Component
public class DiscountPriceCalculator {

    public int calculate(Integer originPrice, List<Promotion> promotions) {
        int discountPrice = 0;

        for (Promotion promotion : promotions) {
            if (promotion.getPromotion_type() == COUPON) {
                discountPrice += promotion.getDiscount_value();
            } else if (promotion.getPromotion_type() == CODE) {
                //TODO 소수점 이하 처리, 현재 반올림
                discountPrice += (originPrice * (promotion.getDiscount_value()/100f));
            }
        }

        return discountPrice;

    }
}
