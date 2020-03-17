package Journalr.com;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import Journalr.com.storage.*;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class JournalrApplication {

  public static void main(String[] args) {
    SpringApplication.run(JournalrApplication.class, args);
  }

  @Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}