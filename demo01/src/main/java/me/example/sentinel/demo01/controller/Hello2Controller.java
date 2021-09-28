package me.example.sentinel.demo01.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.example.sentinel.demo01.config.ResourceConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AOP方式
 *
 * 官方文档： https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81
 *
 * @author zhoujialiang9
 * @date 2021/9/24 19:27
 */
@Slf4j
@RestController
public class Hello2Controller {

    @GetMapping("hello2")
    @SentinelResource(
            value = ResourceConstant.API_HELLO_2,
            blockHandler = "blockHandler",
            fallback = "fallback")
    public void hello2(String name) {
        log.info("hello, {}" , name);
    }

    public void blockHandler(String name, BlockException blockException) {
        log.warn("触发限流,  rule={} ", JSON.toJSONString(blockException.getRule()));
    }

    public void fallback(String name, Throwable e){
        log.error("error");
    }

}
