package br.com.zapzup.manager.application.config

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ExtendWith(SpringExtension::class)
open class BasicIntegrationTest() {

    @Autowired
    protected lateinit var context: WebApplicationContext

    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build()
    }

}
