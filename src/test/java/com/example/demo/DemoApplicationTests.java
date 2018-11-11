package com.example.demo;

import com.example.demo.service.AnimalDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
	@Autowired
	private List<AnimalDao> animalDaos;
	@Test
	public void contextLoads() {
		for (AnimalDao animalDao:animalDaos) {
			animalDao.sing();
		}
	}

}
