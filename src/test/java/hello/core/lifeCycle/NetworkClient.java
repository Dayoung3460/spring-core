package hello.core.lifeCycle;

import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

// 초기화, 소멸 인터페이스 사용(잘 사용 안함)
@Setter
public class NetworkClient implements InitializingBean, DisposableBean {
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

    // 의존관계 주입 끝나고 실행
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        connect();
        call("Initializing connect msg");
    }

    // 빈 종료될 때 호출
    @Override
    public void destroy() throws Exception {
        System.out.println("destroy");
        disconnect();
    }
}
