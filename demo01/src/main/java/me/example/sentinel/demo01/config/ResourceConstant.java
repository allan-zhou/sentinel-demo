package me.example.sentinel.demo01.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2021/9/24 19:55
 */
public class ResourceConstant {
    public static final String API_HELLO = "HelloController.hello";
    public static final String API_HELLO_2 = "HelloController.hello2";
    public static final String API_HELLO_3 = "HelloController.hello3";

    public static final String SERVICE_HELLO = "HelloService.Hello";

    public static List<String> allResource = new ArrayList();

    static {
        allResource.add(API_HELLO);
        allResource.add(API_HELLO_2);
        allResource.add(API_HELLO_3);
        allResource.add(SERVICE_HELLO);
    }
}
