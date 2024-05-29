package antigravity.model.response;

import antigravity.domain.entity.Product;
import antigravity.model.ProductPrice;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductAmountResponse {
    private String name; //상품명

    private String originPrice; //상품 기존 가격
    private String discountPrice; //총 할인 금액
    private String finalPrice; //확정 상품 가격

    public static ProductAmountResponse of(Product product, ProductPrice productPrice) {
        return ProductAmountResponse.builder()
                .name(product.getName())
                .originPrice(String.format("%,d", productPrice.getOriginPrice()))
                .discountPrice(String.format("%,d", productPrice.getDiscountPrice()))
                .finalPrice(String.format("%,d", productPrice.getFinalPrice()))
                .build();
    }
}
