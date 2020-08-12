package com.challenge.jwt.auth.authserver.config

import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.*
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2) //
                .select() //
                .apis(RequestHandlerSelectors.any()) //
                .paths(Predicates.not(PathSelectors.regex("/error"))) //
                .build() //
                .apiInfo(metadata()) //
                .useDefaultResponseMessages(false) //
                .securitySchemes(listOf(apiKey()))
                .securityContexts(listOf(securityContext()))
                .tags(Tag("users", "Operations about users")) //
                .genericModelSubstitutes(Optional::class.java)
    }

    private fun metadata(): ApiInfo {
        return ApiInfoBuilder() //
                .title("JSON Web Token Authentication API") //
                .description("This is a sample JWT authentication service.") //
                .version("1.0.0") //
                .license("MIT License").licenseUrl("http://opensource.org/licenses/MIT") //
                .contact(Contact(null, null, "nicolaas.vercuiel73@gmail.com")) //
                .build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Authorization", "header")
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build()
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "accessEverything")
        val authorizationScopes = arrayOfNulls<AuthorizationScope>(1)
        authorizationScopes[0] = authorizationScope
        return Arrays.asList(SecurityReference("Authorization", authorizationScopes))
    }
}