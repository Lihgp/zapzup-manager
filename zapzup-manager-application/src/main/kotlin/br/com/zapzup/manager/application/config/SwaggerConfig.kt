package br.com.zapzup.manager.application.config

import com.google.common.base.Optional
import com.google.common.base.Predicates
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
open class SwaggerConfig {

    companion object {
        const val BASE_PACKAGE = "br.com.zapzup.manager.application"
    }

    @Bean
    open fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
        .paths(Predicates.not(PathSelectors.regex("/error")))
        .build()
        .apiInfo(metadata())
        .useDefaultResponseMessages(false)
        .genericModelSubstitutes(Optional::class.java)

    private fun metadata(): ApiInfo = ApiInfoBuilder()
        .title("ZapZup Application")
        .build()
}