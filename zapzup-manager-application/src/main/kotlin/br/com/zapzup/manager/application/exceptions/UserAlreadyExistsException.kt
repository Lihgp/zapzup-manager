package br.com.zapzup.manager.application.exceptions

class UserAlreadyExistsException(val field: String) : RuntimeException()