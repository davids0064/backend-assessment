package io.paymeter.assessment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Paymeter Assessment API")
                        .version("1.0")
                        .description("Servicio para la gestión y cálculo de tickets de estacionamiento")
                        .contact(new Contact()
                                .name("Soporte Técnico")
                                .email("soporte@paymeter.io")));
    }

}
