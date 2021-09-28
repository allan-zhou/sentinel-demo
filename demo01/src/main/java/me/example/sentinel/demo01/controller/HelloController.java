package me.example.sentinel.demo01.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.example.sentinel.demo01.config.ResourceConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 基本使用
 *
 * @see com.alibaba.csp.sentinel.SphU
 * @see com.alibaba.csp.sentinel.SphO
 *
 * https://github.com/alibaba/Sentinel/wiki/%E5%A6%82%E4%BD%95%E4%BD%BF%E7%94%A8
 *
 * @author zhoujialiang9
 * @date 2021/8/17 20:24
 */
@Slf4j
@RestController
public class HelloController {

    @GetMapping("hello")
    public void hello(String name) {
        Entry entry = null;
        try {
            entry = SphU.entry(ResourceConstant.API_HELLO);
            // 需要保护的资源
            log.info("hello, {}", name);

        } catch (BlockException e) {
            // 触发限流后的逻辑
            log.info("触发限流, rule={}" , JSON.toJSONString(e.getRule()));

        } catch (Exception e) {
            //　业务异常
            Tracer.trace(e);

        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }

}
