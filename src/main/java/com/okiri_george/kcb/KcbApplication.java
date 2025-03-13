package com.okiri_george.kcb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication

@EnableWebSecurity


public class KcbApplication {


//	@OpenAPIDefinition(
//			info = @Info(title = "B2C Mobile Money API", version = "1.0", description = "Mocked B2C Mobile Money Transaction Service")
//	)

	public static void main(String[] args) {
		SpringApplication.run(KcbApplication.class, args);
	}

}
