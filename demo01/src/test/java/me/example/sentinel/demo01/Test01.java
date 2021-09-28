package me.example.sentinel.demo01;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;

/**
 * @author zhoujialiang9
 * @date 2021/9/1 19:56
 */
@Slf4j
@SpringBootTest
public class Test01 {

    @Test
    public void test1() {
        // 2021-09-01 20:00:00
        long timestamp = 1630497600000L;
        calcTimeIds(timestamp);

        // 2021-09-01 20:00:02
        timestamp = 1630497602000L;
        calcTimeIds(timestamp);

        // 2021-09-01 20:00:59
        timestamp = 1630497659000L;
        calcTimeIds(timestamp);

        // 2021-09-01 20:01:01
        timestamp = 1630497661000L;
        calcTimeIds(timestamp);
    }

    private void calcTimeIds(long timestamp) {
        int sampleCount = 60;

        long timeId = timestamp / 1000;

        int timeIdx = (int) (timeId % sampleCount);

        log.info("timestamp={}, timeId={}, timeIdx={}", timestamp, timeId, timeIdx);
    }


    @Test
    public void test2(){
        int index = 1;
        AtomicReferenceArray<String> atomicReferenceArray = new AtomicReferenceArray<>(10);
        atomicReferenceArray.set(index, "b");

        log.info("value = {}", atomicReferenceArray.get(index));

        atomicReferenceArray.compareAndSet(index, "b", "hello");
        log.info("value = {}", atomicReferenceArray.get(index));

    }
}
