package sample.cafekiosk.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CafeKioskTest {
    @DisplayName("음료 1개를 추가하면 주문대기목록에 담긴다.")
    @Test
    void add_mannual_test() {  // 수동 테스트
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();

        // when
        cafeKiosk.add(new Americano());

        // then
        System.out.println(">> 담긴 음료 수: "+cafeKiosk.getBeverages().size());
        System.out.println(">> 담긴 음료: "+cafeKiosk.getBeverages().get(0).getName());

    }

//    @DisplayName("음료 1개 추가 테스트")
    @DisplayName("음료 1개를 추가시 주문대기목록에 담긴다.")
    @Test
    void add() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();

        // when
        cafeKiosk.add(new Americano());

        // then
        assertThat(cafeKiosk.getBeverages().size()).isEqualTo(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("음료 2개를 한번에 추가시 주문대기목록에 모두 담긴다.")
    @Test
    void addSeveralBeverages() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when
        cafeKiosk.add(americano,2);

        // then
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
        assertThat(cafeKiosk.getBeverages().get(1).getName()).isEqualTo("아메리카노");

    }

    @DisplayName("음료 0개를 추가시 1잔이상 주문요구 메시지가 발생한다.")
    @Test
    void addZeroBeverages() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        // when then
        assertThatThrownBy(()->cafeKiosk.add(americano,0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");

    }

    @DisplayName("아메리카노 1개를 추가 후 제거시 주문대기목록에 아무것도 없다. ")
    @Test
    void remove() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        // when
        cafeKiosk.remove(americano);

        // then
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("라떼와 아메리카노를 담았다가 초기화시 주문대기목록엔 아무것도 없다. ")
    @Test
    void clear() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        // when
        cafeKiosk.clear();

        // then
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("라떼와 아메리카노를 담으면 가격은 8500원이다. ")
    @Test
    void calculateTotalPrice() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        //when
        int totalPrice = cafeKiosk.calculateTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @DisplayName("라떼와 아메리카노를 담고 초기화시 주문대기목록은 비어있다. ")
    @Test
    void createOrder() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        //when
        Order order = cafeKiosk.createOrder(LocalDateTime.now());

        //then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("주문가능한 시간에 주문을 생성시 주문이 생성된다.")
    @Test
    void createOrderWithCurrentTime() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);

        //when
        Order order = cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,10,0));

        //then
        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @DisplayName("영업시간 외에는 주문을 할 수 없다.")
    @Test
    void createOrderOutsideOpenTime() {
        //given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();


        cafeKiosk.add(americano);

        //when then
        assertThatThrownBy(()->cafeKiosk.createOrder(LocalDateTime.of(2023,1,17,9,59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }
}