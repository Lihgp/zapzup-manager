package br.com.zapzup.manager.integration

import br.com.zapzup.manager.integration.jaf.AddressClient
import br.com.zapzup.manager.integration.jaf.AddressFinderErrorDecoder
import feign.Client
import feign.Feign
import feign.Logger
import feign.RequestInterceptor
import feign.RequestTemplate
import feign.Retryer
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import feign.okhttp.OkHttpClient
import feign.opentracing.TracingClient
import feign.slf4j.Slf4jLogger
import io.opentracing.util.GlobalTracer
import org.apache.logging.log4j.ThreadContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
open class FeignConfig(
    private val addressFinderErrorDecoder: AddressFinderErrorDecoder,
    private val client: Client
) {
    @Value("\${address.finder.url}")
    private lateinit var addressFinderUrl: String

    @Bean
    open fun addressFinderClient(): AddressClient = Feign.builder()
//        .client(TracingClient(Client.Default(null, null), GlobalTracer.get()))
        .encoder(GsonEncoder())
        .decoder(GsonDecoder())
        .client(client)
//        .requestInterceptor(requestInterceptor())
        .logger(Slf4jLogger())
        .logLevel(Logger.Level.FULL)
        .retryer(Retryer.NEVER_RETRY)
        .errorDecoder(addressFinderErrorDecoder)
        .target(AddressClient::class.java, addressFinderUrl)

    @Bean
    open fun requestInterceptor(): RequestInterceptor = RequestInterceptor { requestTemplate: RequestTemplate ->
        requestTemplate.header("X-B3-TraceId", ThreadContext.get("X-B3-TraceId"))
        requestTemplate.header("X-B3-SpanId", ThreadContext.get("X-B3-SpanId"))
        requestTemplate.header("x-B3-ParentSpanId", ThreadContext.get("X-B3-TraceId"))
    }

    @Bean
    open fun restTemplate(): RestTemplate = RestTemplate()
}