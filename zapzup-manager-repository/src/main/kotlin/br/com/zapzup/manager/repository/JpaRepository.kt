package br.com.zapzup.manager.repository

import br.com.zapzup.manager.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, String>