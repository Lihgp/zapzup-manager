package br.com.zapzup.manager.application.config

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale
import javax.servlet.http.HttpServletRequest

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["br.com.zapzup.manager.repository"])
@EntityScan(value = ["br.com.zapzup.manager.domain"])
@ComponentScan(basePackages = ["br.com.zapzup.manager"])
@Configuration
class ZapZupApplicationConfig {

    @Bean
    fun localeResolver(): LocaleResolver {
        return SmartLocalResolver()
    }

    class SmartLocalResolver : AcceptHeaderLocaleResolver() {

        override fun resolveLocale(request: HttpServletRequest): Locale {
            val language: String? = request.getHeader("Accept-Language")

            if (StringUtils.isNotEmpty(language)) {
                val locale = language!!.split("_")
                if (locale.size == 2) return Locale(locale[0], locale[1].toUpperCase())
            }

            return Locale.getDefault()
        }
    }

    @Bean
    fun messageSource(): ResourceBundleMessageSource {
        val source = ResourceBundleMessageSource()
        source.setBasenames("messages")
        source.setDefaultEncoding("UTF-8")
        source.setUseCodeAsDefaultMessage(true)
        return source
    }
}