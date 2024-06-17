package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    // 동시성 문제 때문에 HashMap 보다 concurrent hashMap을 쓰는게 좋음.
    // 그냥 개발 예제 용도로 HashMap 쓰는거임
    // ConcurrentHashMap: 멀티스테드 환경에서 안전하게 사용가능.
    // 일반적인 HashMap은 여러 스레드가 동시에 접근할 경우 데이터 손상 발생할 수 있음
    private static Map<Long, Member> store = new HashMap<>();
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
