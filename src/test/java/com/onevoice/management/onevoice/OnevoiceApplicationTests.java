package com.onevoice.management.onevoice;

import com.onevoice.management.onevoice.service.ChiruService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OnevoiceApplicationTests {

	@Autowired(required = false)
	private ChiruService chiruService;

	@Test
	void contextLoads() {
	}


}
