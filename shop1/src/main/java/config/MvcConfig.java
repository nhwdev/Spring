package config;

import intercepter.BoardIntercepter;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Locale;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = {"controller", "dto", "service", "dao", "aop"})
@EnableAspectJAutoProxy // AOP 관련 설정
@EnableWebMvc // 기본 제공되는 web 처리 기능 유지
public class MvcConfig implements WebMvcConfigurer {
    @Bean
    public HandlerMapping handlerMapping() { // 요청 url과 Controller 연결
        RequestMappingHandlerMapping hm = new RequestMappingHandlerMapping();
        hm.setOrder(0);
        return hm;
    }

    @Bean
    public ViewResolver viewResolver() { // 뷰 결정자.
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        vr.setPrefix("/WEB-INF/view/"); // list → /WEB-INF/view/list.jsp
        vr.setSuffix(".jsp");
        return vr;
    }

    // 기본 웹파일 처리를 위한 설정
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 파일 업로드를 위한 설정. enctype="multipart/form-data" 형식의 요청시 처리.
    // 파라미터 값, 파일정보, ... 저장
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver mr = new CommonsMultipartResolver();
        mr.setMaxInMemorySize(1024 * 1024);
        mr.setMaxUploadSize(1024 * 1024);
        return mr;
    }

    //예외처리 객체 : 예외발생시 예외 처리해 주는 객체
    @Bean
    public SimpleMappingExceptionResolver exceptionHandler() {
        SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
        Properties pr = new Properties(); // HashTable의 하위클래스 Properties : key와 value가 문자열
        /*
         * exception.CartException 예외가 발생하면, /WEB-INF/view/exception.jsp를 호출
         */
        pr.put("exception.CartException", "exception");
        pr.put("exception.LoginException", "exception");
        pr.put("exception.ShopException", "exception");
        ser.setExceptionMappings(pr);
        return ser;
    }

    // 메시지를 코드값으로 처리하기 위한 설정
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("messages"); // messages.properties 파일사용
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    // 인터셉터관련 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BoardIntercepter()) // 인터셉터 객체 설정
                .addPathPatterns("/board/write")    // URL 정보 추가
                .addPathPatterns("/board/update")
                .addPathPatterns("/board/delete");
    }

    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver resolver = new FixedLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREA);
        return resolver;
    }

}