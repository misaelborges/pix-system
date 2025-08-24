package com.misael.pix_sistem.core.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pix Sistem")
                        .version("1.0.0")
                        .description("Sistema de Pagamentos Instantâneos.")
                        .contact(new Contact()
                                .name("Mizael Borges")
                                .email("mizaelborges2011@email.com"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addResponses("BadRequest", new ApiResponse().description("Requisição inválida"))
                        .addResponses("NotFound", new ApiResponse().description("Recurso não encontrado"))
                        .addResponses("InternalServerError", new ApiResponse().description("Erro interno do servidor"))
                );
    }

}
