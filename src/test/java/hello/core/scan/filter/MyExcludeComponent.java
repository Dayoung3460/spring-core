package hello.core.scan.filter;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
// MyExcludeComponent가 붙은 클래스들은 컴포넌트스캔에서 제외해줄거임
public @interface MyExcludeComponent {

}
