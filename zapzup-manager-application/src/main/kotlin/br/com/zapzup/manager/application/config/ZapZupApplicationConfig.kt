package br.com.zapzup.manager.application.config

import feign.okhttp.OkHttpClient
import org.apache.commons.lang3.StringUtils
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.ThreadContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Component
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootApplication(scanBasePackages = ["br.com.zapzup.manager"])
@EnableJpaRepositories(basePackages = ["br.com.zapzup.manager.repository"])
@EntityScan(value = ["br.com.zapzup.manager.domain"])
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
open class ZapZupApplicationConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(Interceptor())
    }

    @Bean
    open fun localeResolver(): LocaleResolver {
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
    open fun messageSource(): ResourceBundleMessageSource {
        val source = ResourceBundleMessageSource()
        source.setBasenames("messages")
        source.setDefaultEncoding("UTF-8")
        source.setUseCodeAsDefaultMessage(true)
        return source
    }

    @Bean
    open fun okHttpClient(): OkHttpClient = OkHttpClient()
}

@Component
open class Interceptor : HandlerInterceptorAdapter() {

    override fun postHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any, modelAndView: ModelAndView?) {
        println("hehe")
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
//        response.setHeader("X-B3-SpanId", ThreadContext.get("X-B3-SpanId"))
//        response.setHeader("X-B3-TraceId", ThreadContext.get("X-B3-TraceId"))

        return true
    }
}