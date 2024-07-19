package com.kinglin.pet;

import com.kinglin.pet.config.RabbitmqConfig;
import com.kinglin.pet.util.SnowFlakeUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class PetApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 测试雪花算法生成id
     */
    @Test
    void snowFlakeUtilTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        SnowFlakeUtil snowFlakeUtil = new SnowFlakeUtil(0, 0);
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                Long id = snowFlakeUtil.getNextId();
                System.out.println(id);
            });
        }
    }

    @Test
    public void mqTest() {
        String msg = "这是一条测试消息，看下接受到了吗？";

        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TOPICS_INFORM, "inform.email", msg);
    }

    @Test
    public void streamTest() {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            integerList.add(i);
        }
        AtomicInteger fc = new AtomicInteger();
        // List<Integer> fcList = new ArrayList<>();
        integerList.forEach(e -> {
            fc.getAndIncrement();
        });
        // integerList.forEach(fcList::add);
        AtomicInteger pfc = new AtomicInteger();
        List<Integer> pfcList = Collections.synchronizedList(new ArrayList<>());
        integerList.parallelStream().forEach(e -> {
                    pfc.getAndIncrement();
                }
        );
        integerList.parallelStream().forEach(pfcList::add);
        System.out.println("fc---" + fc);
        // System.out.println("fcList---" + fcList.size());
        System.out.println("pfc---" + pfc);
        System.out.println("pfcList---" + pfcList.size());
    }

    @Test
    public void mapComputeTest() {
        @Data
        class Test {
            private Integer count = 0;

            public Test() {
            }

            public Test(Integer count) {
                this.count = count;
            }
        }
        HashMap<String, Test> map = new HashMap<>();
        map.put("1", new Test(1));
        map.put("3", new Test(30));
        map.put("4", new Test(40));
        map.put("6", new Test(60));
        map.computeIfAbsent("1", (k) -> new Test()).count++;
        map.computeIfAbsent("2", k -> new Test()).count++;
        map.computeIfAbsent("5", k -> new Test()).count++;
        System.out.println(map);
    }

}
