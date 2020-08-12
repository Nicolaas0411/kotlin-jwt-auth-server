package com.challenge.jwt.auth.authserver.model

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name="users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(unique = true, nullable = false)
    var username: @Size(min = 4, max = 255, message = "Minimum username length: 4 characters") String? = ""

    @Column(unique = true, nullable = false)
    var email: String? = null
    var password: @Size(min = 8, message = "Minimum password length: 8 characters") String? = ""

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    var roles: List<Role>? = null
}

