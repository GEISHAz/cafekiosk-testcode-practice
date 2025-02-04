package sample.cafekiosk.spring.api.controller.product.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateReqeust {

    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    private ProductCreateReqeust(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(ProductCreateReqeust request,String nextProductNumber) {
        return Product.builder()
                .name(request.getName())
                .productNumber(nextProductNumber)
                .sellingStatus(request.getSellingStatus())
                .type(request.getType())
                .price(request.getPrice())
                .build();
    }
}
