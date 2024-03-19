package com.kinglin.pet;

import com.kinglin.pet.util.SnowFlakeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class PetApplicationTests {

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

}
