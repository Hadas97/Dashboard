package bwrobotics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "bwrobotics")
public class BwRoboticsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BwRoboticsApplication.class, args);
	}

}
