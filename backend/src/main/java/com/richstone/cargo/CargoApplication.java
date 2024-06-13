package com.richstone.cargo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CargoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CargoApplication.class, args);
    }

}
