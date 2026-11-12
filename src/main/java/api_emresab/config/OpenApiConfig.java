package api_emresab.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API REST - Gestión de Inventario de Polímeros")
                        .version("1.0.0")
                        .description("Documentación técnica de los endpoints para el control y trazabilidad de polímeros reciclables (HDPE, PP, LDPE) de ERS-EMRESAB S.A.S.")
                        .contact(new Contact()
                                .name("Ingeniería de Software - ERS-EMRESAB S.A.S")
                                .url("https://github.com/jairoalberto07/api-emresab")));
    }
}