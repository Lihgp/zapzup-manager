package br.com.zapzup.manager.application

import br.com.zapzup.manager.application.config.ZapZupApplicationConfig
import org.apache.logging.log4j.LogManager
import org.springframework.boot.SpringApplication
import java.net.InetAddress
import java.time.LocalDateTime

object ZapZupApplication {

    private val LOG = LogManager.getLogger(ZapZupApplication::class.java)

    @JvmStatic
    fun main(args: Array<String>) {
        val app = SpringApplication.run(ZapZupApplicationConfig::class.java, *args)

        val applicationName = app.environment.getProperty("spring.application.name")
        val contextPath = app.environment.getProperty("server.servlet.context-path")
        val port = app.environment.getProperty("server.port")
        val hostAddress = InetAddress.getLocalHost().hostAddress
        val currentTime = LocalDateTime.now().toString()

        LOG.info(
            """|
               |------------------------------------------------------------
               |   Application '$applicationName' is running! Access URLs:
               |   Local:      http://127.0.0.1:$port$contextPath
               |   External:   http://$hostAddress:$port$contextPath
               |   Current:    $currentTime
               |------------------------------------------------------------""".trimMargin()
        )
    }
}