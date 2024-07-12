package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUserPrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean bean1 = ac.getBean(ClientBean.class);
        int count1 = bean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int count2 = bean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {
        // ClientBean의 객체의 생성시점에 PrototypeBean이 주입됨
//        private final PrototypeBean prototypeBean;

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            // prototype이 singleton에 들어있지만 빈을 생성할 때마다 새로운 빈을 만들고 싶으면
            // Provider 사용해서 get() 호출할 때마다 새로운 prototypeBean을 생성하면 됨.
            // 라이브러리 깔아야함(implementation 'jakarta.inject:jakarta.inject-api:2.0.1')
            // 스프링 컨테이너에서 매번 PrototypeBean을 가져올 필요없이
            // prototypeBeanProvider.get();로만 필요한 객체를 속 가져올 수 있음.
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

//        public int logic() {
//            // logic()이 호출될 때 이미 prototypeBean은 주입이 된 이후기 때문에
//            // prototypeBean이 prototype이라도 같은 빈을 조회함
//            prototypeBean.addCount();
//            int count = prototypeBean.getCount();
//            return count;
//        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
