package com.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@MapperScan("com.travel.mapper")
public class TravelReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(TravelReservationApplication.class, args);
    }
}