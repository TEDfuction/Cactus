package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class CactusG5Application {

	public static void main(String[] args) {
		SpringApplication.run(CactusG5Application.class, args);
	}

}
