package com.challenge.jwt.auth.authserver.security

import com.challenge.jwt.auth.authserver.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class MyUserDetails : UserDetailsService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.findByUsername(username)
                ?: throw UsernameNotFoundException("User '$username' not found")
        return User //
                .withUsername(username) //
                .password(user.password) //
                .authorities(user.roles) //
                .accountExpired(false) //
                .accountLocked(false) //
                .credentialsExpired(false) //
                .disabled(false) //
                .build()
    }
}