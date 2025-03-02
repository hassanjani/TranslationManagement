package Translation.Management.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class TranslationApplication {
	public static void main(String[] args) {
		SpringApplication.run(TranslationApplication.class, args);
	}
}
