package com.youcandoeat.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//JPA Auditing(감시) 기능을 활성화 하는 애노테이션
@EnableJpaAuditing
@SpringBootApplication
public class YoucandoeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoucandoeatApplication.class, args);
	}

}
