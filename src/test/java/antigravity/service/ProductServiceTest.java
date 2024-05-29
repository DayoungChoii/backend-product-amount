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


@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PromotionProductsRepository promotionProductsRepository;

    @BeforeEach
    void before() {
        Promotion couponPromotion = Promotion.builder()
                .promotion_type(PromotionType.COUPON)
                .discount_value(30_000)
                .build();
        Promotion codePromotion = Promotion.builder()
                .promotion_type(PromotionType.CODE)
                .discount_value(15)
                .build();

        promotionRepository.save(couponPromotion);
        promotionRepository.save(codePromotion);

        Product product = Product.builder()
                .name("피팅노드상품")
                .price(215000)
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