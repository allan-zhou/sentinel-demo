package me.example.sentinel.demo03.service;

import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2021/9/13 11:47
 */
@Slf4j
//@Service
public class FileRuleService {
    @PostConstruct
    private void init(){
        loadRules();
    }

    private void loadRules () {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String flowRulePath = URLDecoder.decode(classLoader.getResource("FlowRule.json").getFile(), "UTF-8");

            // Data source for FlowRule
            ReadableDataSource<String, List<FlowRule>> ds = new FileRefreshableDataSource<>(
                    flowRulePath, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {})
            );
            FlowRuleManager.register2Property(ds.getProperty());

            WritableDataSource<List<FlowRule>> wds = new FileWritableDataSource<>(flowRulePath, this::encodeJson);
            WritableDataSourceRegistry.registerFlowDataSource(wds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }
}
