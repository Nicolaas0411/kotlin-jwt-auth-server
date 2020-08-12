package com.challenge.jwt.auth.authserver.dto

import com.challenge.jwt.auth.authserver.model.Role
import io.swagger.annotations.ApiModelProperty

class UserDto {
    @ApiModelProperty(position = 0)
    var username: String? = null

    @ApiModelProperty(position = 1)
    var email: String? = null

    @ApiModelProperty(position = 2)
    var password: String? = null

    @ApiModelProperty(position = 3)
    var roles: List<Role>? = null

}