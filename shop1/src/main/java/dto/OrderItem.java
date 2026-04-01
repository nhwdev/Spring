package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor // 매개변수 없는 생성자
public class OrderItem {
    private int orderid;  // 주문번호
    private int seq;      // 주문 상품번호
    private int itemid;   // 상품번호
    private int quantity; // 주문수량
    private Item item;    // 상품정보

    public OrderItem(int orderid, int seq, ItemSet itemSet) {
        this.orderid = orderid;
        this.seq = seq;
        this.item = itemSet.getItem();
        this.itemid = itemSet.getItem().getId();
        this.quantity = itemSet.getQuantity();
    }
}
