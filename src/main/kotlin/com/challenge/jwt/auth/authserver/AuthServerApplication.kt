package com.challenge.jwt.auth.authserver

import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableAutoConfiguration(exclude = [SecurityAutoConfiguration::class])
@EnableJpaRepositories
class AuthServerApplication {
	@Bean
	fun modelMapper(): ModelMapper? {
		return ModelMapper()
	}
}

fun main(args: Array<String>) {
	runApplication<AuthServerApplication>(*args)
}
