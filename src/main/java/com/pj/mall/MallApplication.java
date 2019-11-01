package com.pj.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.pj.mall.mapper")
@SpringBootApplication
public class MallApplication {
	public static void main(String[] args) {
		SpringApplication.run(MallApplication.class, args);
	}

}
