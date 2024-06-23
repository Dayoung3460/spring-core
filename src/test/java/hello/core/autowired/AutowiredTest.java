package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {
    @Test
    void AutowiredOption() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        // Member는 빈으로 등록 안되어 있음
        // Autowired할 빈이 없으면 수정자 메서드 자체가 실행 안됨
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            // 실행 안됨
            System.out.println("noBean1 = " + noBean1);
        }

        // @Nullable로 하면 수정자 메서드 호출은 되지만 널임
        // noBean2 = null
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 빈이 없으면 Optional.empty가 됨
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }


}
