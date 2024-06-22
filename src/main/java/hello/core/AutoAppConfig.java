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
// @Component 뿐만 아니라 @Controller, @Service, @Repository, @Configuration 도 스캔 대상에 포함됨. 얘네들 안에 @Component 들어있음
@ComponentScan(
        // member 폴더 하위의 파일에서만 컴포넌트 스캔함. 탐색할 패키지의 시작위치 설정.
        basePackages = "hello.core.member",
        //AutoAppConfig의 패키지(package hello.core;)에서만 스캔
        // 지정 안하면(디폴트): @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치
        basePackageClasses = AutoAppConfig.class,
        // 컴포넌트 스캔에서 제할 것들:
        // 1. @Bean으로 수동으로 등록되어 있는 것들, AppConfig에서 수동으로 등록(@Configuration)해준 것들
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

// 기존 AppConfig와 다르게 @Bean으로 등록한 클래스 없음
public class AutoAppConfig {
    // 빈 중복 테스트하려고 똑같은 이름의 스프링 빈을 수동 등록했음
    // 실행해보면
    // 로그: Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing [Generic bean: class [hello.core.member.MemoryMemberRepository];
    // 수동 빈이 우선시 됨
    // 수동 빈이 자동 빈 오버라이딩
//    @Bean(name = "memoryMemberRepository")
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
