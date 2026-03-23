package kr.gd.mavenstudy;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Spring 환경을 생성하는 환경 설정 프로그램
@Configuration // 환경 설정 프로그램. xml 파일로 설정했음.
// kr.gd.mavenstudy 패키지에 속한 클래스 중 @Component 어노테이션을 가진 클래스를 객체 생성. 클래스를 객체 생성함.
@ComponentScan(basePackages = {"kr.gd.mavenstudy"})
public class AppCtx {

}
