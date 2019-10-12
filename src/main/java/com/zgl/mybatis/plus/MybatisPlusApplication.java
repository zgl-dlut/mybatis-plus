package com.zgl.mybatis.plus;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybatisPlusApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*new GeneratorServiceEntity().generateByTables("goods");*/
	}
}