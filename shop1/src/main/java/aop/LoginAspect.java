package aop;

import dto.User;
import exception.LoginException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
@Aspect
public class LoginAspect {
    /*
     * advice : @Around → 필수메서드 실행 전, 후 호출됨
     * pointcut : controller 패키지에 User로 시작하는 클래스의 메서드 중 idCheck 이름으로 시작하는 메서드 이면서 매개변수목록이 String, HttpSession으로 끝나는 메서드
     */
    @Around("execution(* controller.User*.idCheck*(..)) && args(.., userid, session)")
    public Object userIdCheck(ProceedingJoinPoint joinPoint, String userid, HttpSession session) throws Throwable {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !(loginUser instanceof User)) { // 로그아웃 상태
            throw new LoginException("[idCheck]로그인이 필요합니다.", "login");
        }
        if (!loginUser.getUserid().equals("admin") && !loginUser.getUserid().equals(userid)) {
            throw new LoginException("[idCheck]본인만 확인 가능합니다.", "../user/mypage?userid=" + loginUser.getUserid());
        }
        return joinPoint.proceed();
    }

    @Around("execution(* controller.User*.loginCheck*(..)) && args(.., session)")
    public Object loginCheck(ProceedingJoinPoint joinPoint, HttpSession session) throws Throwable {
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null || !(loginUser instanceof User)) { // 로그아웃 상태
            throw new LoginException("[loginCheck]로그인이 필요합니다.", "login");
        }
        return joinPoint.proceed();
    }
}
