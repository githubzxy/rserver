package com.enovell.yunwei.eureka.rserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaServer
@SpringBootApplication
@PropertySource(value={"rserver-dev.properties"})
//@PropertySource(value={"rserver-prod.properties"})
public class RserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RserverApplication.class, args);
	}
}
