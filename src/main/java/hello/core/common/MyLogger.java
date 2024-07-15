package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
// HTTP 요청 당 bean이 하나씩 생성되고 요청 끝나는 시점에 소멸됨
@Scope(value = "request")
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        // uuid를 저장해두면 다른 요청과 구분 가능
        String uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "]" + "request scope bean create: " + this);
    }

    // 고객 요청이 서버에서 빠져나갈 때 호출됨
    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "]" + "request scope bean close: " + this);
    }
}
