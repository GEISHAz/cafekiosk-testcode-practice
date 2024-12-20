package sample.cafekiosk.spring.unit;

import java.util.ArrayList;
import java.util.List;

public class CafeKiosk {
    private final List<Beverage> beverages = new ArrayList<>();
    public void add(Beverage beverage){
        beverages.add(beverage);
    }
    public void remove(Beverage beverage){
        beverages.remove(beverage);
    }
    public int calculateTotalPrice() {
        int totalPrice = 0;
        for(Beverage b : beverages){
            totalPrice += b.getPrice();
        }
        return totalPrice;
    }
    public void clear(){
        beverages.clear();
    }
}
