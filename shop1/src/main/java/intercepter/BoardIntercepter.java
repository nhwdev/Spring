package intercepter;

import dto.User;
import exception.ShopException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * Intercepter
 *  DispatcherServlet과 Controller 사이에서 동작
 *  요청 URL 기준으로 중간에서 동작
 *
 * Filter 와 차이 : Web Application의 기능. Servlet 보다 먼저 실행
 *
 * 주요 메서드
 *  1. preHandle : Controller 호출 전
 *  2. postHandle : Controller 호출 후
 *  3. afterCompletion : View 호출 후
 */
public class BoardIntercepter implements HandlerInterceptor {
    // /board/write, /board/update, /board/delete 요청시 호출
    // preHandle : BoardControlloer 메서드 실행 전 호출
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String boardid = request.getParameter("boardid"); // Parameter 조회
        HttpSession session = request.getSession(); // Session 객체
        User login = (User) session.getAttribute("loginUser"); // 로그인 정보
        if (boardid == null || boardid.equals("1")) { // 공지사항인 경우
            if (login == null || !login.getUserid().equals("admin")) { // 로그아웃상태 또는 일반사용자 로그인인 경우
                throw new ShopException("관리자만 가능합니다.", request.getContextPath() + "/board/list?boardid=1");
            }
        }
        return true;
    }
}
