package com.hexaware.Career;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CareerCrafterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerCrafterApplication.class, args);
	}
@Bean
public ModelMapper modelMapper() {
    return new ModelMapper();
}
@Bean
public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

}
