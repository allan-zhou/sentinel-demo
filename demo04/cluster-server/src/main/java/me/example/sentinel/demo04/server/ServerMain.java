package me.example.sentinel.demo04.server;


import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;

import java.util.Collections;


/**
 * @author zhoujialiang9
 * @date 2021/9/14 17:52
 */
public class ServerMain {

    public static void main(String[] args) throws Exception {
        ClusterTokenServer tokenServer = new SentinelDefaultTokenServer();

        ClusterServerConfigManager.loadGlobalTransportConfig(new ServerTransportConfig()
                .setIdleSeconds(600)
                .setPort(10080));

        ClusterServerConfigManager.loadServerNamespaceSet(Collections.singleton("demo"));

        tokenServer.start();
    }

}
