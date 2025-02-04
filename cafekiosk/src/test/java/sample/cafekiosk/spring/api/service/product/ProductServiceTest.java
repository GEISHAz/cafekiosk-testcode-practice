package sample.cafekiosk.spring.api.service.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.product.response.ProductResponse;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.HANDMADE;

@ActiveProfiles("test")
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1증가한 값이다.")
    @Test
    void createProduct(){
        // given
        Product product1 = createProduct("001", HANDMADE,SELLING,"아메리카노",4000);
        productRepository.save(product1);

        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("카푸치노")
                .sellingStatus(SELLING)
                .type(HANDMADE)
                .price(5000)
                .build();
        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // then
        assertThat(productResponse)
                .extracting("productNumber","type","sellingStatus","price","name")
                .contains("002",HANDMADE,SELLING,5000,"카푸치노");

        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2)
                .extracting("productNumber","type","sellingStatus","price","name")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE,SELLING,4000,"아메리카노"),
                        tuple("002",HANDMADE,SELLING,5000,"카푸치노")
                );
    }

    @DisplayName("상품이 하나도 없을때 신규 상품의 번호는 001 이다.")
    @Test
    void createProductWhenProductIsEmpty(){
        // given
        ProductCreateRequest request = ProductCreateRequest.builder()
                .name("카푸치노")
                .sellingStatus(SELLING)
                .type(HANDMADE)
                .price(5000)
                .build();
        // when
        ProductResponse productResponse = productService.createProduct(request.toServiceRequest());

        // then
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(1)
                .extracting("productNumber","type","sellingStatus","price","name")
                .containsExactlyInAnyOrder(
                        tuple("001",HANDMADE,SELLING,5000,"카푸치노")
                );;


    }

    private Product createProduct(String productNumber, ProductType productType, ProductSellingStatus productSellingStatus, String name, int price) {
        Product product1 = Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .sellingStatus(productSellingStatus)
                .name(name)
                .price(price)
                .build();
        return product1;
    }
}
