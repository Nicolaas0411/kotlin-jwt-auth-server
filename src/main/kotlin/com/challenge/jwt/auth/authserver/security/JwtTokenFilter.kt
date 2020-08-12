package com.challenge.jwt.auth.authserver.security

import com.challenge.jwt.auth.authserver.util.CustomException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
class JwtTokenFilter(jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {
    private val jwtTokenProvider: JwtTokenProvider = jwtTokenProvider

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, filterChain: FilterChain) {
        val token = jwtTokenProvider.resolveToken(httpServletRequest)
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth: Authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: CustomException) {
            //this is very important, since it guarantees the user is not authenticated at all
            SecurityContextHolder.clearContext()
            httpServletResponse.sendError(ex.httpStatus.value(), ex.message)
            return
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }

}