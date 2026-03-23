package annotation;

import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * aop 관련 용어
 * 1) pointcut : 필수 메서드를 설정. AOP 클래스가 적용되는 메서드
 *      execution(public * annotation..*(..))
 *      : annotation 패키지에 속한 모든 클래스의 public 메서드
 *
 *      public : 접근제한자가 public인 메서드
 *      *      : 리턴타입과 상관없음
 *      ..     : 모든 클래스. 클래스명에 상관❌
 *      *(..)  : 매개변수와 상관없이 모든 메서드
 *
 * 2) advice : AOP 클래스가 실행되는 시점 설정
 *      @Before : pointcut 메서드 실행 전에 실행
 *      @AfterReturning : pointcut 메서드 정상 실행 종료 후 실행
 *            returning : 필수 메서드의 리턴 객체
 *      @AfterThrowing : pointcut 메서드 오류 실행 종료 후 실행
 *            throwing : 예외객체 전달
 *      @After : pointcut 메서드 종료 후 실행
 *      @Around : pointcut 메서드 실행 전 후에 실행
 */
@Component // 객체화
@Aspect    // AOP 클래스
@Order(3) // 3번째 순서
public class LoggingAspect {
    final String publicMethod = "execution(public * annotation..*(..))";

    @Before(publicMethod)
    public void before() {
        System.out.println("[LA] Before 메서드 실행 전 실행");
    }

    @AfterReturning(pointcut = publicMethod, returning = "ret")
    public void afterReturning(Object ret) {
        System.out.println("[LA] AfterReturning 메서드 정상 종료 후 실행. 리턴값=" + ret);
    }

    // Throwable : Exception, Error 클래스의 상위 클래스
    @AfterThrowing(pointcut = publicMethod, throwing = "ex")
    public void afterThrowing(Throwable ex) {
        System.out.println("[LA] AfterThrowing 메서드 예외 종료 후 실행. 예외메시지 = " + ex.getMessage());
    }

    @After(publicMethod)
    public void afterFinally() {
        System.out.println("[LA] After 메서드 종료 후 실행");
}
}
