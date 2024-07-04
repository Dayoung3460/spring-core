package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RequiredArgsConstructor // final이 붙은 필드를 파라미터로 받는 생성자를 만들어줌.. 롬복 아주 강려크...
public class OrderServiceImpl implements OrderService {

    // memberRepository, discountPolicy는 불변. 밖에서 이 둘을 맘대로 못 바꿈
    // final 있으면 생성자에서 꼭 초기화 시켜줘야햠
    // 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로 final 사용 불가.
    // 생성자 주입 방식만 final 키워드 사용 가능.
    // memberRepository, discountPolicy 둘 다 null일 수 없음
    @Getter
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자(public OrderServiceImpl(){})가 하나면 @Autowired 안해도 자동으로 의존관계 주입됨
    // 생성자 주입: 생성자 호출시점에 딱 한 번만 호출되는 것이 보장됨
    // 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계 변경할 일이 없음. 변경하면 안됨(불변의 법칙)
    // 객체 생성할 때 딱 한번만 호출되는게 좋음

    // 자동 의존 관계 주입할 때 빈이 2개이면(fixDiscountPolicy, rateDiscountPolicy 둘 다 @Component로 빈 등록되어 있을 때)...?
    // 오류: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy
    // 1: 필드 명을 정하고 싶은 빈 이름으로 변경: 타입 매칭(DiscountPolicy) 시도 후, 빈이 여러개 있을 때 파라미터로 받은 애(rateDiscountPolicy)가 매칭이 됨
    // 2: @Qualifier
    // 3. @Primary: 우선 순위 지정
    // @Qualifier, @Primary 둘 다 있을 땐 @Qualifier가 우선.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        // 생성자 외에는 final 멤버 변수를 변경할 수 없음. 객체의 불변성 유지.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 이렇게 해버리면 큰일나. 누가 밖에서 discountPolicy를 수정할 수 있잖아.
    // 불변의 법칙 어김
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
