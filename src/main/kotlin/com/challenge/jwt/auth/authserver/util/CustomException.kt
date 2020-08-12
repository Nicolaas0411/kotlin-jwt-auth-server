package com.challenge.jwt.auth.authserver.util

import org.springframework.http.HttpStatus


class CustomException(override val message: String, val httpStatus: HttpStatus) : RuntimeException() {

    companion object {
        private const val serialVersionUID = 1L
    }

}