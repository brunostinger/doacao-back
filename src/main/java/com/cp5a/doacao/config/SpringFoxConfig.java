package com.cp5a.doacao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2

public class SpringFoxConfig {

    public static final String EVENT_TAG = "";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.cp5a.doacao"))
                .build()
                .apiInfo(apiInfo())
                .tags(
                        new Tag("Endpoint '/event'", "Operações relacionadas aos eventos/palestras"),
                        new Tag("Endpoint '/occupationarea'", "Operações relacionadas a causas/áreas de atuação"),
                        new Tag("Endpoint '/transaction'", "Operações relacionadas a doações/compras de ingressos"),
                        new Tag("Endpoint '/user'", "Operações relacionadas aos usuários/Instituições")
                );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API doAcao")
                .description("Documentação para acesso a API doacao")
                .version("1.0.0")
                .contact(new Contact("Bruno Roger", "", "brunostinger@outlook.com"))
                .build();
    }


}
