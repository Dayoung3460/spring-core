package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration을 등록하지 않으면 CGLIB가 실행되지 않아서 싱글톤 보장이 안됨.
@Configuration
// 실제 동작에 필요한 구현 객체를 여기서 생성 및 연결
// 스프링 컨테이너는 싱글톤 레지스트리.

public class AppConfig {
    // memberService() 호출하면서 memberRepository()가 호출되고
    // orderService()가 호출되면서 memberRepository() 또 호출됨
    // 동일한 객체가 두개 생성됨. 싱글톤 깨지는 것 처럼 보임

    @Bean
    // return 된 객체들이 스프링 컨테이너에 등록됨
    // 메서드 이름 = 스프링 빈 이름
    // 빈 이름은 중복되면 안됨
    public MemberService memberService() {
        System.out.println("Call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        System.out.println("Call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        System.out.println("Call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }

}
