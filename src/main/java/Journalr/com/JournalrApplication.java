package Journalr.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("Journalr.com")
public class JournalrApplication {

  public static void main(String[] args) {
    SpringApplication.run(JournalrApplication.class, args);
  }

}