package id.achfajar.challenge4;

import id.achfajar.challenge4.controller.BinarFudController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "id.achfajar.challenge4")
public class Challenge4Application {

	public static void main(String[] args) {
		BinarFudController bc = SpringApplication.run(Challenge4Application.class, args)
				.getBean(BinarFudController.class);
		bc.welcome();
	}

}
