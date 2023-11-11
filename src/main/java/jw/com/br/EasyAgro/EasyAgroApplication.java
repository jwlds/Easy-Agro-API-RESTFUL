package jw.com.br.EasyAgro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class EasyAgroApplication {

	public static void main(String[] args) {

		SpringApplication.run(EasyAgroApplication.class, args);
	}

}
