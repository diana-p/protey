package com.test.protey;

import com.test.protey.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class ProteyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProteyApplication.class, args);
	}

}
