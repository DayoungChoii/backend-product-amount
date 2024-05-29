package antigravity.service;

import antigravity.domain.entity.Promotion;
import antigravity.exception.ProductAmountException;
import antigravity.model.ProductPrice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static antigravity.constant.ProductAmountExceptionCode.*;

@Component
public class PromotionValidator {
    public List<Promotion> validate(List<Promotion> promotions, int[] coupons) {
        List<Promotion> resultPromotions = new ArrayList<>();

        Map<Integer, Promotion> promotionMap = promotions.stream()
                .collect(Collectors.toMap(Promotion::getId, promotion -> promotion));

        for (int coupon : coupons) {
            if (!promotionMap.containsKey(coupon)) {
                throw new ProductAmountException(INVALID_PROMOTION);
            }

            Promotion promotion = promotionMap.get(coupon);
            Date today = new Date();
            if (today.before(promotion.getUse_started_at()) || today.after(promotion.getUse_ended_at())) {
                throw new ProductAmountException(INVALID_USE_AT);
            }

            resultPromotions.add(promotion);
        }

        return resultPromotions;
    }
}
