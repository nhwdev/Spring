package main;

import annotation.*;
import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main01 {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        // service : ReadArticleServiceImpl 객체
        ReadArticleService service = ctx.getBean("readArticleService", ReadArticleService.class);
        try {
            // 필수 메서드. aop(loggingAspect)의 pointcut으로 설정된 메서드
            Article a1 = service.getArticleAndReadCnt(1);
            System.out.println(a1);
            Article a2 = service.getArticleAndReadCnt(1);
            System.out.println("[main] a1 == a2 : " + (a1 == a2));
            service.getArticleAndReadCnt(0);
        } catch (Exception e) {
            System.out.println("[main] " + e.getMessage());
        }
        System.out.println("\nUpdateMemberInfoTraceAspect 연습");
        // ms : MemberService 객체
        MemberService ms = ctx.getBean("memberService", MemberService.class);
        ms.regist(new Member()); // LoggingAspect 클래스의 pointcut 메서드
        // LoggingAspect 클래스의 pointcut 메서드, UpdateTraceAspect 클래스의 pointcut 메서드
        ms.update("hong", new UpdateInfo());
        // LoggingAspect 클래스의 pointcut 메서드, UpdateTraceAspect 클래스의 pointcut 메서드
        ms.delete("hong2", "text", new UpdateInfo()); // LoggingAspect 클래스의 pointcut 메서드
    }
}
