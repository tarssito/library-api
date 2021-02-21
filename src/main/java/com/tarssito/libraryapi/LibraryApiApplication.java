package com.tarssito.libraryapi;

import com.tarssito.libraryapi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class LibraryApiApplication {

	@Autowired
	private EmailService emailService;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			List<String> emails = List.of("library-api-c7344a@inbox.mailtrap.io");
			emailService.sendMails("Testando serviço de emails", emails);
			log.info("EMAILS ENVIADOS");
		};
	}


	public static void main(String[] args) {
		SpringApplication.run(LibraryApiApplication.class, args);
	}

}
