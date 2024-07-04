package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component

// 직접 만든 어노테이션.
// @Qualifier()에 문자("mainDiscountPolicy")를 넣어야하는데 문자열은 컴파일 단계에서 오류를 못 잡음.
// 문자열은 오타날 확룰이 큼.
// 직접 어노테이션 만들어서 이렇게 사용 가능함
// 코드 추적도 할 수 있고.
// @MainDiscountPolicy 파일 안에 @Qualifier("mainDiscountPolicy")가 포함되어 있음
// 어노테이션은 상속 개념이 없음.
// 근데 무분별하게 어노테이션 만드는건 지양하기. 스프링에서 만들어주는거 최대한 사용하기
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{
    private int discountPercent = 10;


    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP) {
            return price * discountPercent / 100;
        } else {
            return 0;
        }
    }
}
