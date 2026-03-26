package dto;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<ItemSet> itemSetList = new ArrayList<>();

    public List<ItemSet> getItemSetList() {
        return itemSetList;
    }

    public void push(ItemSet itemSet) {
        // itemSet: DB에서 읽은 Item 객체, 수량 저장. 장바구니에 추가될 ItemSet 객체
        // 장바구니에 등록된 ItemSet의 Item 객체와 등록예정인 ItemSet의 Item 객체를 비교
        // 같은 객체(삼품)가 존재 : List 객체에 수정
        // 같은 객체(상품)가 없으면 : List 객체에 ItemSet 객체를 추가
        // itemSetList : 기존에 추가된 ItemSet 객체 목록
        for (ItemSet set : itemSetList) {
            if (set.getItem().getId() == itemSet.getItem().getId()) { // 같은 객체(상품)가 존재하는 경우
                set.setQuantity(set.getQuantity() + itemSet.getQuantity()); // 기존객체에 수량 증가
                return;
            }
        }
        // 같은 객체(상품)가 없으면 itemSetList 객체에 추가
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
