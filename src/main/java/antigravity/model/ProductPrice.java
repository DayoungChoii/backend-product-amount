package antigravity.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductPrice {
    private int originPrice; //상품 기존 가격
    private int discountPrice; //총 할인 금액
    private int finalPrice; //확정 상품 가격
}
