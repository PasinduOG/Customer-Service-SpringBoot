package edu.icet;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerSvApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerSvApplication.class);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}