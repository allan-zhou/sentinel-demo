package me.example.sentinel.demo01.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 初始化规则
 *
 * @see com.alibaba.csp.sentinel.slots.block.Rule
 *
 * 共支持5种类型规则
 * @see FlowRule
 * @see com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule
 * @see com.alibaba.csp.sentinel.slots.system.SystemRule
 * @see com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule
 * @see com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule
 *
 * @author zhoujialiang9
 * @date 2021/9/24 19:47
 */
@Slf4j
@Configuration
public class RuleConfiguration {

    @PostConstruct
    private void init(){
        initFlowRules();
    }

    private  void initFlowRules() {
        List<FlowRule> rules = new ArrayList<FlowRule>();

        for (String resourceName : ResourceConstant.allResource) {
            FlowRule rule = new FlowRule();
            rule.setResource(resourceName);
            rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
            rule.setCount(20);  // Set limit QPS to 20.

            if(resourceName.equals(ResourceConstant.SERVICE_HELLO)) {
                rule.setCount(10);
            }

            rules.add(rule);
        }

        FlowRuleManager.loadRules(rules);
    }

}
