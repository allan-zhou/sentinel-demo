package me.example.sentinel.demo04.client;

import com.alibaba.csp.sentinel.init.InitExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhoujialiang9
 * @date 2021/9/14 18:30
 */
@SpringBootApplication(scanBasePackages = "me.example.sentinel.demo04")
public class ClientApplication {
    public static void main(String[] args) {
        //triggerSentinelInit();

        SpringApplication.run(ClientApplication.class, args);
    }

    private static void triggerSentinelInit() {
        new Thread(() -> InitExecutor.doInit()).start();
    }

}
