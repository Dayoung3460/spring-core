package hello.core.lifeCycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest() {
        // ConfigurableApplicationContext 가 AnnotationConfigApplicationContext 의 상위 인터페이스
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // 빈 등록 초기화, 소멸 메소드: @Bean에 직접 메소드 이름 지정
        // 코드가 아니라 설정 정보를 사용 -> 코드 고칠 수 없는 라이브러리에서도 초기화, 종료 메서드 적용 가능

        // destroyMethod 들아가보면 AbstractBeanDefinition.INFER_METHOD
        // 추론 메서드로 설정되어 있음(기본값이 destroyMethod = "(inferred)")
        // 라이브러리 대부분 close, shutdown이라는 이름의 종료 메서드를 사용함.
        // 직접 스프링 빈으로 등록하면 종료 메서드는 따로 적어주지 않아도 알아서 추론해서 close나 shutdown이라는 메서드를 호출해줌
        // 추론 기능 사용하기 싫으면 -> destroyMethod = ""
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            // 의존관계 주입이 끝나기 전에 setUrl을 하면 url null로 나옴.
            // 의존관계 주입이 완료된 시점에 초기화 작업(객체가 외부랑 연결이 되어서 처음 일 시작하는 단계. 객체가 생성되는 시점 말고)을 해야함
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}

// 스프링 빈 이벤트 라이브사이클(싱글톤일 때)
// 1. 스프링 컨테이너 생성
// 2. 스프링 빈 생성
// 3. 의존관계 주입
// 4. 초기화 콜백(스프링: "이제 초기화 작업 끝났으니까 니 하고 싶은 작업 해")
// 5. 사용(어플리케이션 동작)
// 6. 소멸 전 콜백(빈 소멸 직전에 호출)
// 7. 스프링 종료

// 객체 생성과 초기화는 분리하는게 좋음
// 객체 생성해서 최소 필요한 데이터 세팅하고 메모리 먹은 후에 초기화.
// 생성자 안에서 무거운 초기화 작업을 하는건 지양
// 초기화는 진짜 어플리케이션이 동작하는 단계