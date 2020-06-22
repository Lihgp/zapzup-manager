package br.com.zapzup.manager.service.user

import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Test

class UserServiceTest {

    @Test
    fun `should dummy test`() {
        val test = 1

        Assert.assertSame(1, test)
        Assertions.assertThat(test).isEqualTo(1)
    }
}