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
                        .title("API - Pix System")
                        .version("1.0.0")
                        .description("""
                                API REST responsável pelo gerenciamento de contas bancárias,
                                chaves PIX e transferências financeiras via sistema de pagamentos instantâneos.

                                Funcionalidades principais:
                                    - Cadastro e gerenciamento de contas
                                    - Consulta de saldo e dados da conta
                                    - Criação, listagem, validação e exclusão de chaves PIX
                                    - Realização de transferências via PIX
                                    - Consulta de transferências e histórico por conta
                                    - Validações de regras de negócio
                                    - Tratamento e padronização de exceções
                                """)
                        .contact(new Contact()
                                .name("Misael Borges")
                                .email("mizaelborges2011@email.com")
                                .url("https://github.com/misaelborges"))
                        .license(new License()
                                .name("MIT")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addResponses("BadRequest", new ApiResponse().description("Requisição inválida"))
                        .addResponses("NotFound", new ApiResponse().description("Recurso não encontrado"))
                        .addResponses("Conflict", new ApiResponse().description("Conflito de dados ou regra de negócio"))
                        .addResponses("InternalServerError", new ApiResponse().description("Erro interno do servidor"))
                );
    }
}