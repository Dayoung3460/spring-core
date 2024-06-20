package hello.core.scan.filter;

import java.lang.annotation.*;

// TYPE: 클래스 레벨이 타켓
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// MyIncludeComponent가 붙은 클래스들은 컴포넌트스캔에 추가해줄거임
public @interface MyIncludeComponent {

}
