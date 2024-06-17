package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component: MemberServiceImpl class가 스프링 빈으로 등록.
// 이 때 스프링 빈의 기본 이름은 클래스명의 앞글자만 소문자로 변경됨(memberServiceImpl)
// 직접 이름 지정하고 싶으면 @Component("memberServiceImpl2")
@Component
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    // 기존 AppConfig에서는 메소드에 @Bean 붙이고 리턴되는 객체가 스프링 빈으로 등록되고
    // 그 객체의 파라미터로 들어온 객체가 의존관계로 주입됐었는데
    // 생성자에 @Autowired 붙여줌으로써 자동으로 의존관계가 주입됨
    // 컴포넌트 스캔을 사용하게 되면 의존관계 설정할 수 있는 장소가 없어서(기존에는 AppConfig에서)
    // 자동으로 해주는 @Autowired를 사용하게 돼있음.
    // 자동으로 ac.getBean(MemberRepository.class) 이 코드가 들어간다고 보면 됨
    // 매개변수 타입을 보고 찾아서 주입함(MemberRepository 타입인 MemoryMemberRepository를 스프링 컨테이너에 등록함. 빈 이름은 memoryMemberRepository로)
    // 매개변수 여러개라도 알아서 주입해줌
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
