package com.compiledideas.crewsecback.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfigs {
    @Bean
    public OpenAPIDefinition defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:5000");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("ABDENASSAR AMIMI");
        myContact.setEmail("abdenassaramimi@gmail.com");

        Info information = new Info()
                .title("Blanzin API")
                .version("1.0")
                .description("This API exposes endpoints to manage blanzin startup.")
                .contact(myContact);
        return (OpenAPIDefinition) new OpenAPI().info(information).servers(List.of(server));
    }
}
