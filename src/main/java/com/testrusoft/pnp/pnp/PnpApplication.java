package com.testrusoft.pnp.pnp;

import io.swagger.annotations.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {
		CarsController.class
})
public class PnpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PnpApplication.class, args);
	}
}
