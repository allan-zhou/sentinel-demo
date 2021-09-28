package me.example.sentinel.demo01.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.example.sentinel.demo01.config.ResourceConstant;
import me.example.sentinel.demo01.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2021/9/24 19:30
 */
@Slf4j
@RestController
public class Hello3Controller {
    @Autowired
    private HelloService helloService;

    @PostConstruct
    protected void init() {
        // 定义规则
        //initFlowRules();
    }

    @GetMapping("hello3")
    @SentinelResource(
            value = ResourceConstant.API_HELLO_3,
            blockHandler = "blockHandler",
            fallback = "fallback")
    public void hello3(String name) {

        // 调用 service 方法
        helloService.Hello(name);

        log.info("hello, {}" , name);

    }

    public void blockHandler(String name, BlockException blockException) {
        log.warn("触发限流, rule={} " , JSON.toJSONString(blockException.getRule()));
    }

    public void fallback(String name, Throwable e){
        log.error("error");
    }
}
