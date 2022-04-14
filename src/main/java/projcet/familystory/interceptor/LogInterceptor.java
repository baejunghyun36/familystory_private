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
    public static final String LOG_ID = "logId";

    //다른 트랜잭션이 바꿔치기 하면 안돼
    //ctrl+o  단축키

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //여기다가 담아서 전달해줘야 다른 트랜잭션이 바꿔치기 못해. ctrl+shift+alt+ t  -> introduceConstant
        request.setAttribute(LOG_ID, uuid);

        //RequestMapping : HandlerMethod가 넘어오고,
        //정적 리소스 : ResourceHttpRequestHandler 가 넘어와
        if (handler instanceof HandlerMethod) { //
            HandlerMethod hm = (HandlerMethod) handler; //호출할 컨트롤러 메서드의 모든 정보가 포함되어 있다.
        }

        log.info("REQUEST [{}][{}][{}]", uuid,requestURI, handler);
//        handler : 어떤 컨트롤러가 들어오는지.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        Object logId = (String) request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}][{}][{}]", logId, requestURI, handler);
        if (ex != null) {
            log.error("afterCompletion error!!", ex);//오류는 {}이거 안해도 돼.
        }
    }


}
