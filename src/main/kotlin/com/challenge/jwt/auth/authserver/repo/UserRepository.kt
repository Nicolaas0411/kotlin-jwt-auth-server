package com.challenge.jwt.auth.authserver.repo

import com.challenge.jwt.auth.authserver.model.User
import org.springframework.data.jpa.repository.JpaRepository
import javax.transaction.Transactional


interface UserRepository : JpaRepository<User?, Int?> {
    fun existsByUsername(username: String?): Boolean
    fun findByUsername(username: String?): User?

    @Transactional
    fun deleteByUsername(username: String?)
}