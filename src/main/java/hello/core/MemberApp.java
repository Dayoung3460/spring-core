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
        // ApplicationContext(인터페이스임) = 스프링 컨테이너
        // 스프링 컨테이너 안에 스프링 빈 저장소가 있음(빈 이름, 빈 객체로 이루어짐)
        // 스프링 컨테이너는 xml 기반으로 만들 수도 있고 어노테이션 기반 자바 설정 클래스로 만들수도있음
        // 기존에는 AppConfig 사용해서 직접 객체 생성, DI 했지만 이젠 스프링 컨테이너 통해서함.
        // AnnotationConfigApplicationContext는 ApplicationContext의 구현체
        // ApplicationContext는 BeanFactory를 상속받고 있음.
        // BeanFactory의 기능(빈 관리 및 검색) 모두 제공받음. 그 외 부가기능 가지고 있음
        // BeanFactory도 스프링 컨테이너라 함
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
