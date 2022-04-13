package projcet.familystory.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static final String LOG_ID = "logId";

    //ctrl+o  단축키


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        //여기다가 담아서 전달해줘야. ctrl+shift+alt+ t  -> introduceConstant
        request.setAttribute(LOG_ID, uuid);


        log.info("REQUEST [{}][{}][{}]", uuid,requestURI, handler);


    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        Object logId = request.getAttribute(LOG_ID);

    }
}
