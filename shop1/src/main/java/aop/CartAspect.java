package aop;

import dto.Cart;
import exception.CartException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class CartAspect {
    /*
     * pointcut : 필수 메서드
    *   * : 접근 제한자, 리턴타입 상관없음
    *   controller.Cart* : controller 패키지의 Cart로 시작하는 클래스
     *  check*(..) : 매개변수 목록과 상관없는 메서드의 이름이 check로 시작하는 메서드
     *  args(.., session) : 매개변수 목록의 마지막 매개변수값이 HttpSession 타입인 메서드
     *
     * advice : @Before. 필수메서드(pointcut 으로 지정된 메서드) 실행 전
     */
    @Before("execution(* controller.Cart*.check*(..)) && args(..,session)")
    public void cartCheck(HttpSession session) throws Throwable {
        Cart cart = (Cart) session.getAttribute("cart"); // 등록된 장바구니 객체
        if(cart == null || cart.getItemSetList().isEmpty()) { // 장바구니에 상품이 없는 경우
            throw new CartException("장바구니에 상품을 추가하세요.", "../item/list");
            // 예외 발생시 정상적인 수행을 멈추고, 예외처리 알고리즘 실행
        }
    }
}
