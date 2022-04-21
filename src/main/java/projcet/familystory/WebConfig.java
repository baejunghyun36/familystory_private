package projcet.familystory;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import projcet.exception.interceptor.LogInterceptor1;
import projcet.familystory.argumentresolver.LoginMemberArgumentResolver;


import projcet.familystory.interceptor.LoginCheckIntercept;

import javax.servlet.Filter;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    //WebMvcConfigurer 실행.

    //ctrl+o
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //만든 애노테이션 추가
        resolvers.add(new LoginMemberArgumentResolver());

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor1())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "**.ico", "/error", "/error-page/**"); // 오류 페이지 경로
    }


    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //인터셉트 순서 체인식으로 적용. order(2) 가 있으면 .order(1)을 수행하고 다음 인터셉트 적용.
//        registry.addInterceptor(new LoginCheckIntercept())
//                .order(1) //첫번째 인터셉트
//                .addPathPatterns("/**") //하위 전부 허용하되,
//                .excludePathPatterns("/","/css/**", "/*.ico", "/error", "/signUp" ); //이 url 에는 인터셉트 먹이지마.
//    }

/*
    필터 적용 코드. 하지만 인터셉트를 사용하기 때문에 주석.
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean logCheckFilter(){

        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LoginCheckFilter());//화이트 리스트 넣어놈
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }*/








}
