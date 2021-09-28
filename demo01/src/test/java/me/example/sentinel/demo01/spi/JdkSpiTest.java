package me.example.sentinel.demo01.spi;

import com.alibaba.csp.sentinel.init.InitFunc;
import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;


/**
 * @author zhoujialiang9
 * @date 2021/9/18 16:21
 */
public class JdkSpiTest {

    @Test
    public void JdkSpiTest1() throws Exception {
        ServiceLoader<InitFunc> initFuncs = ServiceLoader.load(InitFunc.class);

        Iterator<InitFunc> initFuncIterator = initFuncs.iterator();
        while (initFuncIterator.hasNext()) {
            // 必须在遍历迭代器的时候，才能通过Class#forName 方法加载类并且通过反射创建实例
            InitFunc initFunc = initFuncIterator.next();

            initFunc.init();
        }

    }
}
