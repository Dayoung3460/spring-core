package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        // @bean했던 애들을 관리해줌
        // @Configuration 해줬던 class를 매개변수로 넣어줌
        // ApplicationContext = 스프링 컨테이너
        // 기존에는 AppConfig 사용해서 직접 객체 생성, DI 했지만 이젠 스프링 컨테이너 통해서함.
        // 스프링 컨테이너 장점
            // 1.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // 메서드 이름(memberService)으로 빈이 등록되어 있음
        // 두번째 파라미터는 memberService의 타입 명시
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);


        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find Member: " + findMember.getName());
    }
}
