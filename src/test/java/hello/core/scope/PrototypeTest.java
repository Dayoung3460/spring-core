package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrototypeTest {
    @Test
    void prototypeBeanTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        // getBean 할 때 init()이 호출됨
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        System.out.println("find prototypeBean2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        System.out.println("bean1: " + bean1);
        System.out.println("bean2: " + bean2);
        // 참조값이 서로 다름
        assertThat(bean1).isNotSameAs(bean2);

        // 스프링 컨테이너 닫았는데도 destroy() 살행안됨
        ac.close();

        // 이렇게 직접 닫아줘야함
        bean1.destroy();
        bean2.destroy();
    }

    // 빈 스코프가 prototype일 때는 스크링 컨테이너가 객체 생성, 의존관계 주입, 초기화까지만 관여함.
    // 거의 싱글톤 빈을 사용하고 프로토타입은 가끔 씀
    @Scope("prototype")
    static class PrototypeBean {
        // 프로토타입 빈을 두 번 조회했으므로 init() 두 번 실행됨
        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
