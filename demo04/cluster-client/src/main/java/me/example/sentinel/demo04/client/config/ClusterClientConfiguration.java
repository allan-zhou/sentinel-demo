package me.example.sentinel.demo04.client.config;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.registry.ConfigSupplierRegistry;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2021/9/15 17:11
 */
@Configuration
public class ClusterClientConfiguration {
    private String ruleKey = "sentinel.cluster.rules.flow.ruleKey";
    private String channel = "sentinel.cluster.rules.flow.channel";

    @PostConstruct
    private void init() {
        // 初始化客户端配置
        initClientConfig();

        // 设置cluster server
        initClientAssignConfig();

        // 初始化流控规则
        initClientFlowRuleProperty();
    }

    private void initClientFlowRuleProperty() {
        // redis
        RedisConnectionConfig config = RedisConnectionConfig.builder()
                .withHost("localhost")
                .withPort(6379)
                .build();
        Converter<String, List<FlowRule>> flowConfigParser = source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        });
        ReadableDataSource<String, List<FlowRule>> dataSource = new RedisDataSource<List<FlowRule>>(config, ruleKey, channel, flowConfigParser);
        // 设置客户端Flow规则
        FlowRuleManager.register2Property(dataSource.getProperty());

    }

    private void initClientConfig() {
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);

        ClusterClientConfig clusterClientConfig = new ClusterClientConfig();
        clusterClientConfig.setRequestTimeout(1000);

        ClusterClientConfigManager.applyNewConfig(clusterClientConfig);

    }

    private void initClientAssignConfig() {
        // 先指定名称空间
        // 当客户端连接上服务端时，会立即发送一个 PING 类型的消息给服务端，Sentinel 将名称空间携带在 PING 数据包上传递给服务端，服务端以此获得每个客户端连接的名称空间。
        //ConfigSupplierRegistry.setNamespaceSupplier(() -> SentinelConfig.getConfig(SentinelConfig.APP_NAME_PROP_KEY));
        ConfigSupplierRegistry.setNamespaceSupplier(() -> "demo");

        ClusterClientAssignConfig clusterClientAssignConfig = new ClusterClientAssignConfig();
        clusterClientAssignConfig.setServerHost("localhost");
        clusterClientAssignConfig.setServerPort(10080);
        ClusterClientConfigManager.applyNewAssignConfig(clusterClientAssignConfig);
    }

}
