package ru.freelib.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String schemeName = "cookieAuth";
        return new OpenAPI()
//                .info(new Info()
//                        .title("FreeLib API")
//                        .version("1.0.0")
//                        .description("REST API семестрового проекта. Аутентификация через HttpOnly JWT-куки.")
//                        .contact(new Contact().name("TimurSibgatullin").email("TiRSibgatullin@kpfu.ru")))
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(schemeName,
                                new SecurityScheme()
                                        .name(schemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.COOKIE)
                                        .name("ACCESS_TOKEN")));
    }
}