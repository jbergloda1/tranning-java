package com.dc24.tranning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TranningApplication {
	public static void main(String[] args) {
		SpringApplication.run(TranningApplication.class, args);
	}

}


