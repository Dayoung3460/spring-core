package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {
    @Test
    void singletonBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean bean1 = ac.getBean(SingletonBean.class);
        SingletonBean bean2 = ac.getBean(SingletonBean.class);
        System.out.println("bean2 = " + bean1);
        System.out.println("bean2 = " + bean2);
        // 참조 주소 같음
        assertThat(bean1).isSameAs(bean2);
        ac.close();

    }

    // bean 주기가 singleton일 때는 Scope가 객체 생성, 의존관계 주입, 초기화 ~ 종료까지이고
    // 스프링 컨테이너가 객체 생성할 때 항상 같은 객체를 만들고 호출함
    // 싱글톤 빈은 스프링 컨테이너가 첨부터 끝까지 다 관여함.
    @Scope("singleton")
    static class SingletonBean {
        // 빈을 두 번 만들었는데도 init()은 한 번만 실행됨. 두 개라도 같은 빈이니까
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }

    }
}
