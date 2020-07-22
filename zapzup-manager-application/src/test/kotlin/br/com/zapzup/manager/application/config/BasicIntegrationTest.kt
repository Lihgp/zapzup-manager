package br.com.zapzup.manager.application.config

import br.com.zapzup.manager.repository.ChatRepository
import br.com.zapzup.manager.repository.MessageRepository
import br.com.zapzup.manager.repository.TokenRepository
import br.com.zapzup.manager.repository.UserRepository
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ContextConfiguration(classes = [ZapZupApplicationConfig::class])
@RunWith(SpringRunner::class)
@ActiveProfiles(value = ["test"])
abstract class BasicIntegrationTest {

    @Autowired
    protected lateinit var context: WebApplicationContext

    @Autowired
    protected lateinit var userRepository: UserRepository

    @Autowired
    protected lateinit var messageRepository: MessageRepository

    @Autowired
    protected lateinit var chatRepository: ChatRepository

    @Autowired
    protected lateinit var passwordEncoder: BCryptPasswordEncoder

    @Autowired
    protected lateinit var tokenRepository: TokenRepository

    protected lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build()
    }
}
