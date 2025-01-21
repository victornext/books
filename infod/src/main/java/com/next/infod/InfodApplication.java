package com.next.infod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InfodApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfodApplication.class, args);
	}

}
