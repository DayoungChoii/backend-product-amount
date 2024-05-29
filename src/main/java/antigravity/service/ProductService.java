package antigravity.service;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.Promotion;
import antigravity.domain.entity.PromotionProducts;
import antigravity.exception.ProductAmountException;
import antigravity.model.ProductPrice;
import antigravity.model.request.ProductInfoRequest;
import antigravity.model.response.ProductAmountResponse;
import antigravity.repository.ProductRepository;
import antigravity.repository.PromotionProductsRepository;
import antigravity.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static antigravity.constant.ProductAmountExceptionCode.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PromotionProductsRepository promotionProductsRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionValidator promotionValidator;
    private final DiscountPriceCalculator discountPriceCalculator;
    private final PriceValidator priceValidator;


    public ProductAmountResponse getProductAmount(ProductInfoRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new ProductAmountException(INVALID_PRODUCT));

        if (product.isOutOfPriceRange()) {
            throw new ProductAmountException(OUT_OF_PRICE_RANGE);
        }

        List<PromotionProducts> promotionProductsList = promotionProductsRepository.findByProductId(request.getProductId());
        List<Promotion> promotions = promotionRepository.findByIdIn(getPromotionIds(promotionProductsList));

        List<Promotion> validatedPromotions = promotionValidator.validate(promotions, request.getCouponIds());


        int discountPrice = discountPriceCalculator.calculate(product.getPrice(), validatedPromotions);
        ProductPrice productPrice = priceValidator.validateAndGetFinalPrice(product.getPrice(), discountPrice);

        return ProductAmountResponse.of(product, productPrice);
    }

    private static List<Integer> getPromotionIds(List<PromotionProducts> promotionProductsList) {
        return promotionProductsList.stream()
                .map(promotionProducts -> promotionProducts.getPromotion().getId())
                .toList();
    }

    private boolean doesPromotionExist(List<PromotionProducts> promotionProductsList) {
        return !promotionProductsList.isEmpty();
    }
}
