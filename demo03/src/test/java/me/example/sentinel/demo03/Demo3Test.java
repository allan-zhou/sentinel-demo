package me.example.sentinel.demo03;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhoujialiang9
 * @date 2021/9/8 20:51
 */
@Slf4j
@SpringBootTest
public class Demo3Test {
    @Test
    public void test1(){
        ClassLoader classLoader = getClass().getClassLoader();
        log.info(classLoader.getClass().getName());

        URL url = classLoader.getResource("FlowRule.json");
        log.info("url={}", JSON.toJSONString(url));
    }

    @Test
    public void test2() throws Exception {
        Class intClazz = Integer.class;
        Method method1 = intClazz.getMethod("parseInt", String.class, int.class);
        System.out.println(method1.invoke(null, "10", 10));

        Class[] params =  new Class[]{ String.class, int.class};
        Method method2 = intClazz.getMethod("parseInt", params);
        System.out.println(method2.invoke(null, "10", 10));

        Class longClass = Long.class;
        Method method3 = longClass.getMethod("parseLong", params);
        System.out.println(method3.invoke(null, "F", 16));
    }

    @Test
    public void test3() throws Exception {
        AtomicInteger i = new AtomicInteger();
        log.info("i={}", i.incrementAndGet());

        AtomicInteger i2 = new AtomicInteger();
        log.info("i={}", i2.getAndIncrement());
    }
}
