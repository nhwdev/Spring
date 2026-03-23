package annotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class UpdateTraceAspect {
    /*
     * args(.., id, info) : 매개변수 목록을 이용하여 pointcut 설정
     *      .., id, info  : 마지막의 2개의 매개변수는 id:String, info:UpdateInfo인 메서드
     *
     * argNames="ret, id, info" : 매개변수 설명 → ret타입 : Object, id 타입 : String, info 타입 : UpdateInfo
     */
    @AfterReturning(pointcut = "args(.., id, info)", argNames = "ret, id, info", returning = "ret")
    public void traceReturn(Object ret, String memberid, UpdateInfo info) {
        System.out.println("[TA] 정보 수정 결과: " + ret + ", 대상ID: " + memberid + ", 수정정보: " + info);
    }
}
