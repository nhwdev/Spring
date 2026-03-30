package aop; // aop 패키지 선언

import dto.User; // 사용자 정보 DTO 클래스 임포트
import exception.ShopException; // 커스텀 예외 클래스 임포트
import org.aspectj.lang.ProceedingJoinPoint; // AOP 조인포인트 임포트
import org.aspectj.lang.annotation.Around; // Around 어드바이스 어노테이션 임포트
import org.aspectj.lang.annotation.Aspect; // Aspect 어노테이션 임포트
import org.springframework.stereotype.Component; // Spring 컴포넌트 어노테이션 임포트
import org.springframework.web.context.request.RequestContextHolder; // 현재 요청 정보 홀더 임포트
import org.springframework.web.context.request.ServletRequestAttributes; // 서블릿 요청 속성 임포트

import javax.servlet.http.HttpServletRequest; // HTTP 요청 객체 임포트
import javax.servlet.http.HttpSession; // HTTP 세션 객체 임포트

@Aspect // 이 클래스를 AOP Aspect로 선언
@Component // Spring 빈으로 등록
public class AdminLoginAspect {
    @Around("execution(* controller.AdminController.*(..))") // AdminController의 모든 메서드 실행 전후에 적용
    public Object adminCheck(ProceedingJoinPoint joinPoint)
            throws Throwable {
        HttpSession session = null; // 세션 변수 초기화
        // RequestContextHolder : request, response 객체 전달
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); // 현재 요청의 속성 가져오기
        if (attributes != null) { // 요청 속성이 존재하는 경우
            HttpServletRequest request = attributes.getRequest(); // HTTP 요청 객체 추출
            session = request.getSession(); // 요청에서 세션 추출 
        }
        User loginUser = (User) session.getAttribute("loginUser"); // 세션에서 로그인 사용자 정보 가져오기
        if (loginUser == null || !(loginUser instanceof User)) { // 로그인 사용자가 없거나 User 타입이 아닌 경우
            throw new ShopException("[adminCheck]로그인 하세요.", "../user/login"); // 로그인 요청 예외 발생
        } else if (!loginUser.getUserid().equals("admin")) { // 로그인 사용자가 admin이 아닌 경우
            throw new ShopException("[adminCheck]관리자만 가능합니다.", "../user/mypage?userid=" + loginUser.getUserid()); // 관리자 권한 없음 예외 발생
        }
        return joinPoint.proceed(); // 모든 검증 통과 시 원래 메서드 실행
    }
}