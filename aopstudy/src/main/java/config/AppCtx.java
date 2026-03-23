package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"annotation"})
@EnableAspectJAutoProxy // AOP 설정. AOP 관련 어노테이션을 인식
public class AppCtx {

}
