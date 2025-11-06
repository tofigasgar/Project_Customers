package az.customers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    //http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Swagger Open API Customer")
                .description("Sample API for demonstrating Swagger in Spring Boot 3.5.0")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Tofig Asgarov")
                        .email("tofig.asgar@gmail.com")
                        .url("example"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("url")));
    }
}
