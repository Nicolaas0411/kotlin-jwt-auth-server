package com.challenge.jwt.auth.authserver.controller

import com.challenge.jwt.auth.authserver.dto.UserDto
import com.challenge.jwt.auth.authserver.dto.UserRespDto
import com.challenge.jwt.auth.authserver.model.User
import com.challenge.jwt.auth.authserver.service.UserService
import io.swagger.annotations.*
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
@CrossOrigin
@Api(tags = ["users"])
class UserController {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val modelMapper: ModelMapper? = null

    @PostMapping("/signin")
    @ApiOperation(value = "\${UserController.signin}")
    @ApiResponses(value = [ApiResponse(code = 400, message = "Something went wrong"), ApiResponse(code = 422, message = "Invalid username/password supplied")])
    fun login( //
            @ApiParam("Username") @RequestParam username: String?,  //
            @ApiParam("Password") @RequestParam password: String?): String {
        return userService!!.signin(username, password)
    }

    @PostMapping("/signup")
    @ApiOperation(value = "\${UserController.signup}")
    @ApiResponses(value = [ApiResponse(code = 400, message = "Something went wrong"), ApiResponse(code = 403, message = "Access denied"), ApiResponse(code = 422, message = "Username is already in use")])
    fun signup(@ApiParam("Signup User") @RequestBody user: UserDto?): String {
        return userService!!.signup(modelMapper!!.map(user, User::class.java))
    }

    @DeleteMapping(value = ["/{username}"])
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "\${UserController.delete}", authorizations = [Authorization(value = "apiKey")])
    @ApiResponses(value = [ApiResponse(code = 400, message = "Something went wrong"), ApiResponse(code = 403, message = "Access denied"), ApiResponse(code = 404, message = "The user doesn't exist"), ApiResponse(code = 500, message = "Expired or invalid JWT token")])
    fun delete(@ApiParam("Username") @PathVariable username: String): String {
        userService!!.delete(username)
        return username
    }

    @GetMapping(value = ["/{username}"])
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "\${UserController.search}", response = UserRespDto::class, authorizations = [Authorization(value = "apiKey")])
    @ApiResponses(value = [ApiResponse(code = 400, message = "Something went wrong"), ApiResponse(code = 403, message = "Access denied"), ApiResponse(code = 404, message = "The user doesn't exist"), ApiResponse(code = 500, message = "Expired or invalid JWT token")])
    fun search(@ApiParam("Username") @PathVariable username: String?): UserRespDto {
        return modelMapper!!.map(userService!!.search(username), UserRespDto::class.java)
    }

    @GetMapping(value = ["/me"])
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "\${UserController.me}", response = UserRespDto::class, authorizations = [Authorization(value = "apiKey")])
    @ApiResponses(value = [ApiResponse(code = 400, message = "Something went wrong"), ApiResponse(code = 403, message = "Access denied"), ApiResponse(code = 500, message = "Expired or invalid JWT token")])
    fun whoami(req: HttpServletRequest?): UserRespDto {
        return modelMapper!!.map(userService!!.whoami(req), UserRespDto::class.java)
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    fun refresh(req: HttpServletRequest): String {
        return userService!!.refresh(req.remoteUser)
    }
}