package br.com.zapzup.manager.application.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@TestConfiguration
@Import(ZapZupApplicationConfig::class)
open class ControllerTestConfiguration