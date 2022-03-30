package br.com.spring.securityclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableAsync
public class SecurityClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityClientApplication.class, args);
	}

}
