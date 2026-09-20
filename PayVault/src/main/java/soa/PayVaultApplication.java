package soa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class PayVaultApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayVaultApplication.class, args);
    }

}