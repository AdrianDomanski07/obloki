package com.example.obloki;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class OblokiApplication {

	private static final Logger logger = LoggerFactory.getLogger(OblokiApplication.class);
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(OblokiApplication.class, args);
		logger.info("Data uruchomienia: {}", java.time.LocalDateTime.now());
		logger.info("Autor serwera: {} {}", "Adrian", "Domanski");
		if(context.getEnvironment().getProperty("server.port", Integer.class) != null)
		{
			int serverPort = context.getEnvironment().getProperty("server.port", Integer.class);
			logger.info("Port TCP: {}", serverPort);
		}

	}

}
