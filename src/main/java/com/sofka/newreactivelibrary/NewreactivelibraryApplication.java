package com.sofka.newreactivelibrary;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewreactivelibraryApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(NewreactivelibraryApplication.class, args);
	}

}
