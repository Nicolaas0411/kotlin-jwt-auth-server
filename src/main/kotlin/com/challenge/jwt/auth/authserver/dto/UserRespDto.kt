package com.challenge.jwt.auth.authserver.dto

import com.challenge.jwt.auth.authserver.model.Role
import io.swagger.annotations.ApiModelProperty

class UserRespDto {
    @ApiModelProperty(position = 0)
    var id: Int? = null

    @ApiModelProperty(position = 1)
    var username: String? = null

    @ApiModelProperty(position = 2)
    var email: String? = null

    @ApiModelProperty(position = 3)
    var roles: List<Role>? = null
}