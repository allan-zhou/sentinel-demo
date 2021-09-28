package me.example.sentinel.demo04.server.init;

import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerFlowConfig;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集群限流规则需要在集群限流客户端配置一份，同时集群限流服务端也需要配置一份，缺一不可。
 * 客户端需要取得集群限流规则才会走集群限流模式，而服务端需要取得同样的限流规则，才能正确的回应客户端。
 * 为了统一规则配置，我们应当选择动态配置，让集群限流客户端和集群限流服务端去同一数据源取同一份数据。
 * <p>
 * http://localhost:8719/cluster/server/info
 * <p>
 * 集群流控规则
 * 查看：http://localhost:8719/cluster/server/flowRules
 * 设置：http://localhost:8719/cluster/server/modifyFlowRules?namespace=demo&data=
 * <p>
 * [{"resource":"api.hello","controlBehavior":0,"count":20.0,"grade":1,"limitApp":"default","strategy":0,"clusterMode":true,"clusterConfig":{"flowId":1}}]
 * <p>
 * "[{\"resource\":\"api.hello\",\"controlBehavior\":0,\"count\":21.0,\"grade\":1,\"limitApp\":\"default\",\"strategy\":0,\"clusterMode\":true,\"clusterConfig\":{\"flowId\":1}}]"
 * "[{\"resource\":\"api.hello\",\"controlBehavior\":0,\"count\":20,\"grade\":1,\"limitApp\":\"default\",\"strategy\":0,\"clusterMode\":true,\"clusterConfig\":{\"flowId\":1,\"sampleCount\":10,\"windowIntervalMs\":1000,\"strategy\":0}}]"
 * <p>
 * 集群流控规则
 *
 * @author zhoujialiang9
 * @date 2021/9/15 14:27
 * @see ServerFlowConfig
 */
public class ClusterServerInitFunc implements InitFunc {
    private String ruleKey = "sentinel.cluster.rules.flow.ruleKey";
    private String channel = "sentinel.cluster.rules.flow.channel";

    @Override
    public void init() {
        initClusterFlowRule();
    }

    private void initClusterFlowRule() {
        // redis
        RedisConnectionConfig config = RedisConnectionConfig.builder()
                .withHost("localhost")
                .withPort(6379)
                .build();
        Converter<String, List<FlowRule>> flowConfigParser = source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        });
        ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<List<FlowRule>>(config, ruleKey, channel, flowConfigParser);

        // 设置Flow规则
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            System.out.println("setPropertySupplier namespace=" + namespace);
            return redisDataSource.getProperty();
        });
    }

}
