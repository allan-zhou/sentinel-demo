package me.example.sentinel.demo02.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.DefaultBlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhoujialiang9
 * @date 2021/8/23 14:36
 */
@Slf4j
public class MyBlockExceptionHandler extends DefaultBlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        super.handle(request, response, e);

        log.info("path={} 触发限流规则 rule={}" , request.getRequestURI(), e.getRule());
    }
}
