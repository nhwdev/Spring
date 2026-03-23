package kr.gd.mavenstudy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main01 {
    public static void main(String[] args) {
        /*
         * AnnotationConfigApplicationContext(AppCtx.class) : AppCtx 클래스를 환경설정 파일로 설정
         * → ("executor", Executor 클래스의 객체)
         *
         * ApplicationContext : 컨테이너. (이름, 객체)들을 저장하고 있는 객체
         */
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        // ctx.getBean("executor", Executor.class)
        // → ctx 컨테이너에서 이름이 executor 인 객체를 리턴. 객체의 자료형은 Executor.class
        Executor exec = ctx.getBean("executor", Executor.class);
        exec.addUnit(new WorkUnit());
        exec.addUnit(new WorkUnit());
    }
}

