package br.com.zapzup.manager.infrastructure.exception

class UserAlreadyExistsException(val field: String) : RuntimeException()