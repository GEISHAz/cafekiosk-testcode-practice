package sample.cafekiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.response.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        String nextProductNumber = createNextProductNumber();
        Product savedProduct = productRepository.save(request.toEntity(request, nextProductNumber));
        return createProductResponse(savedProduct,nextProductNumber);
    }

    private String createNextProductNumber(){
        String latestProductNumber = productRepository.findLatestProductNumber();
        
        if(latestProductNumber == null)
            return "001";
        int latestProductNumberInt = Integer.valueOf(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt+1;
        return String.format("%03d",nextProductNumberInt);
    }

    public List<ProductResponse> getSellingProducts(){
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private ProductResponse createProductResponse(Product saved,String nextProductNumber) {
        return ProductResponse.builder()
                .id(saved.getId())
                .productNumber(nextProductNumber)
                .type(saved.getType())
                .price(saved.getPrice())
                .name(saved.getName())
                .sellingStatus(saved.getSellingStatus())
                .build();
    }
}
