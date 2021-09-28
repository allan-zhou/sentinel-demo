package me.example.sentinel.demo02.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @see SentinelWebInterceptor
 *
 *
 * @author zhoujialiang9
 * @date 2021/8/23 13:50
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SentinelWebMvcConfig sentinelWebMvcConfig = new SentinelWebMvcConfig();

        // 配置限流处理器
        sentinelWebMvcConfig.setBlockExceptionHandler(new MyBlockExceptionHandler());
        // 添加SentinelWebInterceptor
        registry.addInterceptor(new SentinelWebInterceptor(sentinelWebMvcConfig))
                .addPathPatterns("/**");
    }

}
