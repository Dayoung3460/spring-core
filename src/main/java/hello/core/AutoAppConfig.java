package hello.core;

//스프링 빈 등록시 @Bean 통해서 했는데 등록해야 할 스크링 빈이 많을 시 아주 귀찮
//        -> 자동으로 스프링 빈 등록 가능: 컴포넌트 스캔
//        -> 의존관계도 자동으로 주입 가능: @Autowired

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// @Configuration 소스 코드 열어보면 @Component 붙어있음
@Configuration

// @Component 어노테이션 붙인 애들을 스캔해서 스프링 빈으로 자동 등록해줌
@ComponentScan(
        // 컴포넌트 스캔에서 제할 것들:
        // 1. @Bean으로 수동으로 등록되어 있는 것들, AppConfig에서 수동으로 등록(@Configuration)해준 것들
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

// 기존 AppConfig와 다르게 @Bean으로 등록한 클래스 없음
public class AutoAppConfig {
}
