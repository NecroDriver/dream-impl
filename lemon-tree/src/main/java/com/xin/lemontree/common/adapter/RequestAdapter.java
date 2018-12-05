package com.xin.lemontree.common.adapter;

import com.xin.lemontree.common.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author creator mafh 2018/2/6 11:49
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@Configuration
public class RequestAdapter implements WebMvcConfigurer {
    /**
     * 登录拦截器
     */
    private RequestInterceptor requestInterceptor;

    /**
     * 自定义拦截器类
     * @param requestInterceptor 拦截器
     */
    @Autowired
    public RequestAdapter(RequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    /**
     * 用于存放不拦截的页面数据
     */
    String[] excludes = new String[]{
            "/swagger-ui.html", // swagger资源
            "/swagger-ui.html/**", // swagger资源
            "/swagger-resources/**", // swagger资源
            "/webjars/**", // swagger资源
            "/v2/**", // swagger资源
            "/js/**", // js资源
            "/css/**", // css资源
            "/images/**", // images资源
            "/login.html", // 登录页面
            "/error.html", // 错误页面
            "/api/user/**",// 用户相关接口
            "/websocket",
            "/mywebsocket"
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("error").setViewName("error.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        //swagger2 放行
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
