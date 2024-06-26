package br.com.vitor.ms01bookservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Ms01BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ms01BookServiceApplication.class, args);
	}

}
