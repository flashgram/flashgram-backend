package com.app.flashgram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FlashgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashgramApplication.class, args);
	}

}
