package Bibliotheque_DAEK.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "Bibliotheque_DAEK")
@EnableJpaRepositories(basePackages = "Bibliotheque_DAEK.Repository")
@EntityScan(basePackages = "Bibliotheque_DAEK.Model")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
