package hello.core.lifeCycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;

@Setter
public class NetworkClient {
    private String url;

    public NetworkClient() {
        System.out.println("Calling constructor " + url);

    }

    // Call when service starts
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // Call when service ends
    public void disconnect() {
        System.out.println("disconnect: " + url);

    }

    // 빈 초기화, 소멸 콜백을 받을 때 어노테이션을 쓰는것이 스프링에서 권장하는 방법임.
    // 패키지가 jakarta.annotation.PostConstruct; 스프링에 종속된 기술이 아니라 자바 표준
    // 단점: 외부 라이브러리에는 적용 불가(@Bean의 initMethod, destroyMethod 기능을 따로 사용해야함)

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("initializing connect msg");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
