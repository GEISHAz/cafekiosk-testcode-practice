package sample.cafekiosk.spring.domain.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StockTest {

    @DisplayName("재고의 수량이 주문 수량보다 작은지 확인한다.")
    @Test
    void isQuantityLessThen(){
        // given
        Stock stock = Stock.create("001", 1);
        int quantity = 2;

        // when
        boolean result = stock.isQuantityLessThen(quantity);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("재고를 주어진 갯수만큼 차감할 수 있다.")
    @Test
    void deductQuantity(){
        // given
        Stock stock = Stock.create("001",2);
        int quantity = 1;

        // when
        stock.deductQuantity(quantity);

        // then
        assertThat(stock.getQuantity()).isEqualTo(1);
    }

    @DisplayName("재고보다 많은 수의 수량으로 차감시도시 예외가 발생한다.")
    @Test
    void deductQuantity2(){
        // given
        Stock stock = Stock.create("001",1);
        int quantity = 2;

        // when // then
        assertThatThrownBy(()->stock.deductQuantity(quantity))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("차감할 재고 수량이 없습니다.");
    }
}