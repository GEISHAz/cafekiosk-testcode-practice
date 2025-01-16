package sample.cafekiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

class ProductTypeTest {

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType(){
        // given
        ProductType givenType = ProductType.HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
    @Test
    void containsStockType2(){
        // given
        List<ProductType> givenType = List.of(
                ProductType.BOTTLE,
                ProductType.BAKERY);

        // when
        List<ProductType> result = givenType.stream()
                .filter(productType -> ProductType.containsStockType(productType))
                .collect(Collectors.toList());

        // then
        assertThat(result).hasSize(2)
                .containsExactlyInAnyOrder(
                        ProductType.BAKERY,
                        ProductType.BOTTLE
                );
    }
}