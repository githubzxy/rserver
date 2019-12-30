package com.enovell.yunwei.km_micor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableEurekaClient
@EnableAspectJAutoProxy
@EnableScheduling
public class KmMicorServiceApplication {

    public static void main(String[] args) {
		/*
		 * final String aString = "aa"; final String b = "aa"; boolean aa =
		 * aString.equals(b); boolean ss = aString == b; System.out.println(ss);
		 */
        SpringApplication.run(KmMicorServiceApplication.class, args);
    }
}
