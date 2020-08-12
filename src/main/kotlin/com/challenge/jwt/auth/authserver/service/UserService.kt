package com.challenge.jwt.auth.authserver.service

import com.challenge.jwt.auth.authserver.model.User
import com.challenge.jwt.auth.authserver.repo.UserRepository
import com.challenge.jwt.auth.authserver.security.JwtTokenProvider
import com.challenge.jwt.auth.authserver.util.CustomException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


@Service
class UserService {
    @Autowired
    private val userRepository: UserRepository? = null

    @Autowired
    private val passwordEncoder: PasswordEncoder? = null

    @Autowired
    private val jwtTokenProvider: JwtTokenProvider? = null

    @Autowired
    private val authenticationManager: AuthenticationManager? = null
    fun signin(username: String?, password: String?): String {
        return try {
            authenticationManager!!.authenticate(UsernamePasswordAuthenticationToken(username, password))
            jwtTokenProvider!!.createToken(username, userRepository!!.findByUsername(username)!!.roles!!)
        } catch (e: AuthenticationException) {
            throw CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    fun signup(user: User): String {
        return if (!userRepository!!.existsByUsername(user.username)) {
            user.password = (passwordEncoder!!.encode(user.password))
            userRepository.save(user)
            jwtTokenProvider!!.createToken(user.username, user.roles)
        } else {
            throw CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY)
        }
    }

    fun delete(username: String?) {
        userRepository!!.deleteByUsername(username)
    }

    fun search(username: String?): User {
        return userRepository!!.findByUsername(username)
                ?: throw CustomException("The user doesn't exist", HttpStatus.NOT_FOUND)
    }

    fun whoami(req: HttpServletRequest?): User? {
        return userRepository!!.findByUsername(jwtTokenProvider!!.getUsername(jwtTokenProvider.resolveToken(req!!)))
    }

    fun refresh(username: String?): String {
        return jwtTokenProvider!!.createToken(username, userRepository!!.findByUsername(username)!!.roles!!)
    }
}