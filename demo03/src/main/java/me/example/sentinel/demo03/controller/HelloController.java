package me.example.sentinel.demo03.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 *
 * @author zhoujialiang9
 * @date 2021/9/8 20:31
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("hello")
    @SentinelResource(value = "HelloController.hello",
            blockHandler = "blockHandler",
            fallback = "fallback")
    public void hello(String name) {
        log.info("hello, {}" , name);
    }

    public void blockHandler(String name, BlockException blockException) {
        log.warn("触发限流, name={}, rule={} ", name , JSON.toJSONString(blockException.getRule()));
    }

    public void fallback(String name, Throwable e) {
        log.error("error");
    }
}
