package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
@EnableAsync
@EnableCaching
public class StudentApplication {

	public static void main(String[] args) {
		
//		String customTmpDir = "/path/to/custom/tmp/dir";
//        System.setProperty("java.io.tmpdir", customTmpDir);
		SpringApplication.run(StudentApplication.class, args);
	}
	
	


}
