package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    void configurationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        // 3개의 스프링 빈이 모두 같은 빈
        System.out.println("memberRepository = " + memberRepository1);
        System.out.println("memberRepository2 = " + memberRepository2);
        System.out.println("memberRepository3 = " + memberRepository);

        Assertions.assertThat(memberRepository1).isSameAs(memberRepository);
        Assertions.assertThat(memberRepository2).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // hello.core.AppConfig$$SpringCGLIB$$0 // 이렇게 출력됨
        // 보통 클래스면 hello.core.AppConfig 이렇게 출력됨
        // SpringCGLIB 가 붙으면 내가 만든 클래스가 아니고 스프링이 CGLIB라는 바이트코드 조작 라이브러리 사용해서
        // AppConfig 클래스 상속받은 임의의 클래스 만들고,
        // 그 클래스를 스프링 빈으로 등록한거임
        // 부모(AppConfig) -> 자식 인스턴스(AppConfig@CGLIB)
        // AppConfig@CGLIB 얘가 싱글톤을 보장되도록 해줌
        // @Bean이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환,
        // 스프링 빈이 없으면 생성해서 스프링 빈으로 동록하도록.
        // CGLIB = Configuration Library..?
        System.out.println("bean = " + bean.getClass());
    }
}
