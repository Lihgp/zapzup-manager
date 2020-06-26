package br.com.zapzup.manager.application.config

import br.com.zapzup.manager.repository.UserRepository
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [ControllerTestConfiguration::class, ZapZupApplicationConfig::class])
@RunWith(SpringRunner::class)
@ActiveProfiles(value = ["test"])
open class BasicIntegrationTest {

    @Autowired
    protected lateinit var context: WebApplicationContext

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var passwordEncoder: BCryptPasswordEncoder

    protected lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build()
    }

}
