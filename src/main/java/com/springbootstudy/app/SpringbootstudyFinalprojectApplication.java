package com.springbootstudy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityListeners;

@SpringBootApplication
@EnableJpaAuditing
public class SpringbootstudyFinalprojectApplication {

	public static void main(String[] args) {
		
		//환경 변수 .env  실행위해 필수
        Dotenv dotenv = Dotenv.load();
        System.setProperty("AWS_ACCESS_KEY_ID", dotenv.get("AWS_ACCESS_KEY_ID"));
        System.setProperty("AWS_SECRET_ACCESS_KEY", dotenv.get("AWS_SECRET_ACCESS_KEY"));

		SpringApplication.run(SpringbootstudyFinalprojectApplication.class, args);
	}

}
