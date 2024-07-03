package com.example.simple;

import org.springframework.boot.SpringApplication;

public class TestSimpleApplication {

	public static void main(String[] args) {
		SpringApplication.from(SimpleApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
