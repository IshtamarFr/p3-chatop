package fr.chatop.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Chatop API")
                .description("API to take care of rentals all around the worlds")
                .version("SNAPSHOT 0.0.1")
                .build();
    }

   @Bean
   public Docket api() {
       return new Docket(DocumentationType.SWAGGER_2)
               .useDefaultResponseMessages(false)
               .select()
               .apis(RequestHandlerSelectors.basePackage("fr.chatop.api.controller"))
               .paths(PathSelectors.any())
               .build()
               .apiInfo(apiInfo())
               .securitySchemes(List.of(apiKey()));
   }

    private ApiKey apiKey() {
        return new ApiKey("jwtToken", "Authorization", "header");
    }
}
