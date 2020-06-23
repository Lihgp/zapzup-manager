package br.com.zapzup.manager.commons.exceptions

class UserAlreadyExistsException(val field: String) : RuntimeException()