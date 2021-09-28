package me.example.sentinel.demo01.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import me.example.sentinel.demo01.config.ResourceConstant;
import org.springframework.stereotype.Service;

/**
 * @author zhoujialiang9
 * @date 2021/9/24 19:24
 */
@Slf4j
@Service
public class HelloService {

    @SentinelResource(
            value = ResourceConstant.SERVICE_HELLO,
            blockHandler = "blockHandler")
    public void Hello(String name) {

        log.info("HelloService.hello, name={}", name);

    }

    public void blockHandler(String name, BlockException blockException) {
        log.warn("触发限流, name={}, rule={} ", name , JSON.toJSONString(blockException.getRule()));
    }

}
