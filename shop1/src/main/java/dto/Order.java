package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Order {
    private int orderid;
    private String userid;
    private Date orderdate;
    private User user;
    private List<OrderItem> itemList = new ArrayList<>(); // 주문 상품 목록

    public int getTotal() {
        /*
         * itemList.stream() : SaleItem 객체를 Stream 으로 리턴
         * mapToInt(int) : Stream 객체를 IntStream 객체로 변형
         *  s : SaleItem 객체
         *  s.getItem().getPrice() : 가격
         *  s.getQuantity()        : 수량
         *  (가격 * 수량).sum()
         *  → SaleItme 객체의 가격 * 수량 데이터를 IntStream으로 리턴
         *
         * sum() : IntStream의 모든 요소들의 합 리턴
         */
        return itemList.stream().mapToInt(s -> s.getItem().getPrice() * s.getQuantity()).sum();
    }
}
