package controller;

import dto.Cart;
import dto.Item;
import dto.ItemSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ItemService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    ItemService service;

    // 장바구니에 상품 추가
    @RequestMapping("cartAdd")
    public String add(Integer id, Integer quantity, HttpSession session, Model model) {
        Item item = service.getItem(id);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        cart.push(new ItemSet(item, quantity));
        model.addAttribute("message", item.getName() + ": " + quantity + "개 장바구니에 추가");
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @RequestMapping("cartView")
    public String View(Integer id, Integer quantity, HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        model.addAttribute("message", "장바구니 상품 조회");
        session.setAttribute("cart", cart);
        return "cart/cart";
    }

    @RequestMapping("cartDelete")
    public String delete(Integer index, HttpSession session, Model model) {
        Cart cart = (Cart) session.getAttribute("cart");
        ItemSet deleteItem = cart.getItemSetList().remove((int) index);
        /*
         * Object List.remove(int index) : index에 해당하는 객체를 제거하여 제거된 객체를 리턴
         * boolean List.remove(Object obj) : obj객체를 List에서 제거 성공 / 실패 boolean으로 리턴
         */
        model.addAttribute("message", "장바구니에서 " + deleteItem.getItem().getName() + "상품이 삭제 되었습니다.");
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    @RequestMapping("checkout")
    public String checkout(HttpSession session) {
        return null; // cart/checkout.jsp 요청
    }
}
