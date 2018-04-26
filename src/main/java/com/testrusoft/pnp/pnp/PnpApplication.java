package com.testrusoft.pnp.pnp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PnpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PnpApplication.class, args);
	}
}
