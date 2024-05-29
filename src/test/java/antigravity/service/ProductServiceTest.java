package antigravity.service;

import antigravity.constant.PromotionType;
import antigravity.domain.entity.Product;
import antigravity.domain.entity.Promotion;
import antigravity.domain.entity.PromotionProducts;
import antigravity.model.request.ProductInfoRequest;
import antigravity.model.response.ProductAmountResponse;
import antigravity.repository.ProductRepository;
import antigravity.repository.PromotionProductsRepository;
import antigravity.repository.PromotionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PromotionProductsRepository promotionProductsRepository;

    private final int ORIGIN_PRICE = 215_000;
    private final int COUPON_DISCOUNT_VALUE = 30_000;
    private final int CODE_DISCOUNT_VALUE = 15;

    @BeforeEach
    void before() {
        Promotion couponPromotion = Promotion.builder()
                .promotion_type(PromotionType.COUPON)
                .discount_value(COUPON_DISCOUNT_VALUE)
                .build();
        Promotion codePromotion = Promotion.builder()
                .promotion_type(PromotionType.CODE)
                .discount_value(CODE_DISCOUNT_VALUE)
                .build();

        promotionRepository.save(couponPromotion);
        promotionRepository.save(codePromotion);

        Product product = Product.builder()
                .name("피팅노드상품")
                .price(ORIGIN_PRICE)
                .build();

        productRepository.save(product);

        PromotionProducts couponMapping = PromotionProducts.builder()
                .promotion(couponPromotion)
                .product(product)
                .build();

        PromotionProducts codeMapping = PromotionProducts.builder()
                .promotion(codePromotion)
                .product(product)
                .build();

        promotionProductsRepository.save(couponMapping);
        promotionProductsRepository.save(codeMapping);

    }

    @AfterEach
    void after() {
        promotionProductsRepository.deleteAll();
        promotionRepository.deleteAll();
        productRepository.deleteAll();
    }

    @Test
    void getProductAmount() {
        //given
        ProductInfoRequest request = ProductInfoRequest.builder()
                .productId(1)
                .couponIds(new int[]{1, 2})
                .build();

        //when
        ProductAmountResponse productAmount = productService.getProductAmount(request);

        //then
        assertThat(productAmount.getOriginPrice()).isEqualTo("215,000");
        assertThat(productAmount.getDiscountPrice()).isEqualTo("62,250");
        assertThat(productAmount.getFinalPrice()).isEqualTo("152,000");

    }
}