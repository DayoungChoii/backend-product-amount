package antigravity.service;

import antigravity.constant.PromotionType;
import antigravity.domain.entity.Promotion;
import antigravity.exception.ProductAmountException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static antigravity.constant.ProductAmountExceptionCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PromotionValidatorTest {

    private final int COUPON_DISCOUNT_VALUE = 30_000;
    private final int CODE_DISCOUNT_VALUE = 15;

    @Test
    void priceValidate_normal_test() {
        //given
        Date startedAt = getDateWithMonthOffset(Calendar.MONTH, -1);
        Date endedAt = getDateWithMonthOffset(Calendar.MONTH, 1);

        Promotion couponPromotion = Promotion.builder()
                .id(1)
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();
        Promotion codePromotion = Promotion.builder()
                .id(2)
                .promotion_type(PromotionType.CODE)
                .discount_value(CODE_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);
        promotions.add(codePromotion);

        int[] coupons = new int[]{1, 2};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        List<Promotion> validatedPromotions = promotionValidator.validate(promotions, coupons);

        //then
        assertThat(validatedPromotions).hasSize(2);
        assertThat(validatedPromotions.get(0).getPromotion_type()).isEqualTo(PromotionType.COUPON);
        assertThat(validatedPromotions.get(1).getPromotion_type()).isEqualTo(PromotionType.CODE);
    }

    @Test
    void priceValidate_partial_coupon_test() {
        //given
        Date startedAt = getDateWithMonthOffset(Calendar.MONTH, -1);
        Date endedAt = getDateWithMonthOffset(Calendar.MONTH, 1);

        Promotion couponPromotion = Promotion.builder()
                .id(1)
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();
        Promotion codePromotion = Promotion.builder()
                .id(2)
                .promotion_type(PromotionType.CODE)
                .discount_value(CODE_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);
        promotions.add(codePromotion);

        int[] coupons = new int[]{1};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        List<Promotion> validatedPromotions = promotionValidator.validate(promotions, coupons);

        //then
        assertThat(validatedPromotions).hasSize(1);
        assertThat(validatedPromotions.get(0).getPromotion_type()).isEqualTo(PromotionType.COUPON);
    }

    @Test
    void priceValidate_no_coupon_test() {
        //given
        Date startedAt = getDateWithMonthOffset(Calendar.MONTH, -1);
        Date endedAt = getDateWithMonthOffset(Calendar.MONTH, 1);

        Promotion couponPromotion = Promotion.builder()
                .id(1)
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();
        Promotion codePromotion = Promotion.builder()
                .id(2)
                .promotion_type(PromotionType.CODE)
                .discount_value(CODE_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);
        promotions.add(codePromotion);

        int[] coupons = new int[]{};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        List<Promotion> validatedPromotions = promotionValidator.validate(promotions, coupons);

        //then
        assertThat(validatedPromotions).hasSize(0);
    }

    @Test
    void priceValidate_invalid_param_test() {
        //given
        Date startedAt = getDateWithMonthOffset(Calendar.MONTH, -1);
        Date endedAt = getDateWithMonthOffset(Calendar.MONTH, 1);

        Promotion couponPromotion = Promotion.builder()
                .id(1)
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);

        int[] coupons = new int[]{1, 2};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        ProductAmountException e = assertThrows(
                ProductAmountException.class,
                () -> promotionValidator.validate(promotions, coupons)
        );

        //then
        assertThat(e.getExceptionCode()).isEqualTo(INVALID_PROMOTION);
    }

    @Test
    void priceValidate_no_promotion_test() {
        //given
        List<Promotion> promotions = Collections.emptyList();
        int[] coupons = new int[]{1};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        ProductAmountException e = assertThrows(
                ProductAmountException.class,
                () -> promotionValidator.validate(promotions, coupons)
        );

        //then
        assertThat(e.getExceptionCode()).isEqualTo(INVALID_PROMOTION);
    }

    @Test
    void priceValidate_invalid_use_at_test() {
        //given
        Date startedAt = getDateWithMonthOffset(Calendar.MONTH,1);
        Date endedAt = getDateWithMonthOffset(Calendar.MONTH, 2);

        Promotion couponPromotion = Promotion.builder()
                .id(1)
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .use_started_at(startedAt)
                .use_ended_at(endedAt)
                .build();

        List<Promotion> promotions = new ArrayList<>();
        promotions.add(couponPromotion);

        int[] coupons = new int[]{1};

        //when
        PromotionValidator promotionValidator = new PromotionValidator();
        ProductAmountException e = assertThrows(
                ProductAmountException.class,
                () -> promotionValidator.validate(promotions, coupons)
        );

        //then
        assertThat(e.getExceptionCode()).isEqualTo(INVALID_USE_AT);
    }

    private Date getDateWithMonthOffset(int field, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(field, offset);
        return new Date(calendar.getTimeInMillis());
    }
}