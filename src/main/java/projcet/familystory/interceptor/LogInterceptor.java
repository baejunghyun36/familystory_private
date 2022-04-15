
package projcet.familystory.interceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

//스프링 인터셉터가 서블릿 필터보다 훨씬 많은 기능을 제공한다.
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    //상수로 이름 설정해놨어
    public static final String LOG_ID = "logId";

    // 만약 여기에 Strin uuid 멤버변수를 설정한다면 다른 트랜잭션이 바꿔치기 할 수 있다.
    // 즉, 다른스레드들로 인해 race_condition현상이 발생할 수 있으면 request를 통해 전달해줘야한다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //여기다가 uuid를 담아서 전달해줘야 다른 트랜잭션이 바꿔치기 못해. ctrl+shift+alt+ t  -> introduceConstant
        request.setAttribute(LOG_ID, uuid);

        //RequestMapping은 HandlerMethod가 넘어오고, 정적 리소스는 ResourceHttpRequestHandler가 사용.

        // requestMapping 이니까 handlerMethod의 핸들러가 맞는지 체크.
        if (handler instanceof HandlerMethod) {
            //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
            HandlerMethod hm = (HandlerMethod) handler;
        }

        log.info("REQUEST [{}][{}][{}]", uuid,requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        //String으로 캐스팅 해
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        // 예외가 null이 아니라면
        if (ex != null) {
            //오류는 {}이거 안해도 돼.
            log.error("afterCompletion error!!", ex);
        }
    }


}
