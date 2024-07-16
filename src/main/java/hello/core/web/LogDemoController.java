package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// 스프링 컨테이너가 뜨고 @Controller -> LogDemoController가 스캔됨
// @RequiredArgsConstructor -> 생성자 실행되고 myLogger 의존관계 주입됨
// Error creating bean with name 'myLogger': Scope 'request' is not active for the current thread
// myLogger는 bean 스콥이 request라서 유저가 http request 보내기 전에는 활성화 안됨
// 빈 생성 시점을 의존관계 주입 때가 아니가 request 받을 때로 미뤄야함
// Provider 사용해서 해결 가능

@Controller
@RequiredArgsConstructor
public class LogDemoController {
    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerProvider;

    // maps HTTP requests to handler methods of MVC and REST controllers
    // it maps to logDemo method when the URL pattern is "log-demo"
    @RequestMapping("log-demo")
    // the return value of a method should be used as the body for the response
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();
        // 프린트해보면 순수 클래스가 아님 스프링이 조작한 클래스임(proxyMode = ScopedProxyMode.TARGET_CLASS 에 의해)
        // myLogger = class hello.core.common.MyLogger$$SpringCGLIB$$0
        // CGLIB: 바이트코드 조작하는 라이브러리
        // MyLogger 상속받은 가짜 프록시 객체 생성
        // 스프링 컨테이너에 myLogger라는 이름으로 가짜 프록시 객체가 등록됨
        // 의존관계 주입도 이 프록시 객체가 주입됨.
        // 요청이 왔을 때에 내부에서 진짜 빈을 요청하는 로직(가짜 프록시 객체에 들어있음)이 돌아감
        // (프록시 개념: 앞에서 대신 요청을 받아서 처리해주는 애)
        System.out.println("myLogger = " + myLogger.getClass());
        // myLoggerProvider.getObject() 호출하는 시점까지 request scope 빈 생성 지연시키다가
        // 호출되면 HTTP 요청이 진행중이므로 빈 생성이 정상처리됨
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
//        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }
}
