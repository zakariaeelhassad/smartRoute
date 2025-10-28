package org.example.smartroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class SmartRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartRouteApplication.class, args);
    }

}
