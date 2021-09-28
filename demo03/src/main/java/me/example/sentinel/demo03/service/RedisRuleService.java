package me.example.sentinel.demo03.service;

import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 *
 * 动态规则示例
 * [{"clusterMode":false,"controlBehavior":0,"count":20.0,"grade":1,"limitApp":"default","maxQueueingTimeMs":500,"resource":"HelloController.hello","strategy":0,"warmUpPeriodSec":10}]
 * "[{\"clusterMode\":false,\"controlBehavior\":0,\"count\":20.0,\"grade\":1,\"limitApp\":\"default\",\"maxQueueingTimeMs\":500,\"resource\":\"HelloController.hello\",\"strategy\":0,\"warmUpPeriodSec\":10}]"
 * @author zhoujialiang9
 * @date 2021/9/13 17:47
 */
@Slf4j
@Service
public class RedisRuleService {

    private String ruleKey = "sentinel.rules.flow.ruleKey";
    private String channel = "sentinel.rules.flow.channel";

    @PostConstruct
    private void init(){
        loadRules();
    }

    private void loadRules () {
        try {
            RedisConnectionConfig config = RedisConnectionConfig.builder()
                    .withHost("localhost")
                    .withPort(6379)
                    .build();
            Converter<String, List<FlowRule>> flowConfigParser = buildFlowConfigParser();

            ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<List<FlowRule>>(config, ruleKey, channel, flowConfigParser);
            FlowRuleManager.register2Property(redisDataSource.getProperty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Converter<String, List<FlowRule>> buildFlowConfigParser() {
        return source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {});
    }

}
