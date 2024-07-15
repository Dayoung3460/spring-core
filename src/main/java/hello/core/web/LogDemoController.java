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

    // maps HTTP requests to handler methods of MVC and REST controllers
    // it maps to logDemo method when the URL pattern is "log-demo"
    @RequestMapping("log-demo")
    // the return value of a method should be used as the body for the response
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}
