package com.impact.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "IMPACT API Documentation",
                version = "1.0",
                description = "The IMPACT API provides endpoints for managing inventory, assets, and users in the IMPACT project. This API enables users to interact with the system programmatically and perform various operations on the core entities of the project.",  // Descripción más detallada y profesional
                contact = @Contact(
                        name = "Isaac Felipe Brenes Calderón",
                        email = "isaacfelibrenes1904@gmail.com",
                        url = "https://www.linkedin.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        )
)
public class ImpactCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImpactCoreApplication.class, args);
    }

}
