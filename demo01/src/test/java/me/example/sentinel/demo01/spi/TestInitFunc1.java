package me.example.sentinel.demo01.spi;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.spi.Spi;

/**
 * @author zhoujialiang9
 * @date 2021/9/18 16:41
 */
@Spi(value = "func1", order = 100)
public class TestInitFunc1 implements InitFunc {
    @Override
    public void init() throws Exception {
        System.out.println("TestInitFunc1 init");
    }
}
