package annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
 * Order(1) AOP → Order(2)AOP → Order(3)AOP → 필수 메서드 →
 *  Order(3) AOP → Order(2)AOP → Order(1)AOP
 */
@Component // 객체화
@Aspect // AOP 클래스
@Order(2) // 2번째 순서
public class ArticleCacheAspect {
    private Map<Integer, Article> cache = new HashMap<Integer, Article>();

    /*
     * execution(public * *..ReadArticleService.*(..)) → 이름이 ReadArticleService 클래스의 모든 public 메서드
     *   public : public 메서드
     *   *      : 리턴타입에 상관없이
     *   *..    : 패키지명에 상관없이
     *   ReadArticleService : 클래스명
     *   *(..)  : 메서드의 매개변수
     *
     * @Around : advice 중 필수 메서드의 실행 전과 후 모두
     */
    @Around("execution(public * *..ReadArticleService.*(..))")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        // joinPoint : 실행 순서
        // joinPoint.getArgs()[0] : 필수메서드 실행시 호출한 매개변수 값의 목록
        Integer id = (Integer) joinPoint.getArgs()[0];
        // joinPoint.getSignature().getName() : 필수 메서드의 메서드명
        System.out.println("[ACA] " + joinPoint.getSignature().getName() + "(" + id + ") 메서드 호출 전");
        Article article = cache.get(id);
        if (article != null) {
            System.out.println("[ACA] cache 에서 Article[" + id + "] 가져옴");
            return article; // cache의 객체를 리턴. main으로 바로 리턴됨
        }
        // ret : 필수메서드의 리턴 값
        Object ret = joinPoint.proceed(); // 다음순서 메서드 실행. 필수 메서드 실행
        // 필수 메서드 실행 이후. 필수 메서드에서 예외 발생시 실행❌
        System.out.println("[ACA] " + joinPoint.getSignature().getName() + "(" + id + ") 메서드 호출 후");
        if (ret != null && ret instanceof Article) {
            cache.put(id, (Article) ret); // cache : (1:Article객체)
            System.out.println("[ACA] cache에 Article[" + id + "] 추가함");
        }
        return ret;

    }
}