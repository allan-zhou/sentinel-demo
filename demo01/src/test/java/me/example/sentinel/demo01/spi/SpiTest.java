package me.example.sentinel.demo01.spi;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.spi.SpiLoader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;

/**
 * @author zhoujialiang9
 * @date 2021/9/18 16:47
 */
@Slf4j
public class SpiTest {

    @Test
    public void spiTest1() throws Exception {
        SpiLoader<InitFunc> spiLoader = SpiLoader.of(InitFunc.class);

        List<InitFunc> initFuncList = spiLoader.loadInstanceListSorted();
        for (InitFunc initFunc : initFuncList) {
            initFunc.init();
        }
    }

    @Test
    public void spiTest2() throws Exception {
        SpiLoader<InitFunc> spiLoader = SpiLoader.of(InitFunc.class);

        // 根据别名获取实例
        InitFunc initFunc  = spiLoader.loadInstance("func2");
        log.info("spiLoader.loadInstance by aliasName = fun2 ...");
        initFunc.init();

        // 获取第一个实例
        initFunc  = spiLoader.loadFirstInstance();
        log.info("spiLoader.loadFirstInstance ...");
        initFunc.init();

        // 最低优先级的实例
        initFunc  = spiLoader.loadLowestPriorityInstance();
        log.info("spiLoader.loadLowestPriorityInstance ...");
        initFunc.init();

        // 默认实例
        initFunc  = spiLoader.loadDefaultInstance();
        log.info("spiLoader.loadDefaultInstance ...");
        initFunc.init();
    }
}
