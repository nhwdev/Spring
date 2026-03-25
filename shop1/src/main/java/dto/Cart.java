package dto;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ItemSet> itemSetList = new ArrayList<>();

    public List<ItemSet> getItemSetList() {
        return itemSetList;
    }

    public void push(ItemSet itemSet) {
        for (ItemSet set : itemSetList) {
            if(set.getItem().getName().equals(itemSet.getItem().getName())) {
                set.setQuantity(set.getQuantity()+itemSet.getQuantity());
                return;
            }
        }
        itemSetList.add(itemSet);
    }

    /* 전체 장바구니에 속한 (상품 가격 * 수량의 합)을 출력하도록 수정*/
    public int getTotal() { // total get 프로퍼티
        int total = 0;
        for (ItemSet set : itemSetList) {
            total += set.getItem().getPrice() * set.getQuantity();
        }
        return total;
    }
}
