package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // memberRepository, discountPolicy는 불변. 밖에서 이 둘을 맘대로 못 바꿈
    // final 있으면 생성자에서 꼭 초기화 시켜줘야햠
    // memberRepository, discountPolicy 둘 다 null일 수 없음
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 생성자(public OrderServiceImpl(){})가 하나면 @Autowired 안해도 자동으로 의존관계 주입됨
    // 생성자 주입: 생성자 호출시점에 딱 한 번만 호출되는 것이 보장됨
//    @Autowired
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

    // test
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
