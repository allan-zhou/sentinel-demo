package me.example.sentinel.demo03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhoujialiang9
 * @date 2021/9/8 20:04
 */
@SpringBootApplication(scanBasePackages = "me.example.sentinel.demo03")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
