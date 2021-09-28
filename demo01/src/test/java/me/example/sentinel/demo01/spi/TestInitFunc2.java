package me.example.sentinel.demo01.spi;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.spi.Spi;

/**
 * @author zhoujialiang9
 * @date 2021/9/18 16:42
 */
@Spi(value = "func2", order = 200, isDefault = true)
public class TestInitFunc2 implements InitFunc {
    @Override
    public void init() throws Exception {
        System.out.println("TestInitFunc2 init");
    }
}
