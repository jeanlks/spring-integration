package com.spring.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@SpringBootApplication
@Configuration
@ImportResource("integration-context.xml")
public class SpringIntegrationApplication implements ApplicationRunner{

	@Autowired
	@Qualifier("inputChannel")
	private DirectChannel inputChannel;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Message message = MessageBuilder.withPayload("Hello world")
					.setHeader("key", "header")
					.build();
		MessagingTemplate template =  new MessagingTemplate();
		Message<?> returnMessage = template.sendAndReceive(inputChannel, message);
		System.out.println(returnMessage.getPayload());
	}
}
