package com.misael.pix_sistem;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Pix Sistem",
				version = "1.0.0",
				description = "Sistema de Pagamentos Instantâneos.",
				contact = @Contact(name = "Mizael Borges", email = "mizaelborges2011@email.com"),
				license = @License(name = "MIT", url = "https://opensource.org/licenses/MIT")
		)
)
@SpringBootApplication
public class PixSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PixSystemApplication.class, args);
	}

}
