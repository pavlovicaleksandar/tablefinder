package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.service.JwtTokenService
import com.pavlovicaleksandar.tablefinder.service.Role
import com.pavlovicaleksandar.tablefinder.service.User
import com.pavlovicaleksandar.tablefinder.service.UserInfo
import com.pavlovicaleksandar.tablefinder.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import org.springframework.http.ResponseEntity.badRequest
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(
    private val service: UserService,
    private val jwtTokenService: JwtTokenService
) {

    @PostMapping("/register")
    fun register(@RequestBody user: RegisterUserDTO): ResponseEntity<Any> {
        val check = service.checkIsEmailOrUsernameTaken(user.username, user.email)
        if(check != null) {
            return badRequest().body(check)
        }
        val success = service.createUser(user)
        return ResponseEntity(success, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseEntity<LoginResponseDTO> {
        val user = service.getUserByUsernameAndPassword(login.username, login.password)
        return if (user != null) {
            val token = jwtTokenService.createToken(user)
            ResponseEntity(
                LoginResponseDTO(
                    user.id,
                    user.username,
                    user.email,
                    user.role,
                    token
                ),
                HttpStatus.OK
            )
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("{userId}")
    fun updateUser(@RequestBody updateUserDTO: UpdateUserDTO, @PathVariable userId: UUID): ResponseEntity<Unit> {
        service.updateUser(userId, updateUserDTO)
        return ok().build()
    }

    @GetMapping("/userInfo")
    fun getUserInfo(authentication: Authentication): ResponseEntity<UserInfo> {
        return ResponseEntity(authentication.toUser(), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<User> {
        val user = service.getUserById(id)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = service.getAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @GetMapping("moderators")
    fun getAllModerators(): ResponseEntity<List<User>> {
        val users = service.getAllModerators()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @DeleteMapping("/{username}")
    fun deleteUserByUsername(@PathVariable username: String): ResponseEntity<Any> {
        service.deleteByUsername(username)
        return ResponseEntity.noContent().build()
    }
}

data class UpdateUserDTO(val email: String?, val role: Role?, val phoneNumber: String?, val password: String?)

data class LoginResponseDTO(
    val id: UUID,
    val username: String,
    val email: String,
    val role: Role,
    val token: String
)

data class LoginDTO(val username: String, val password: String)

data class RegisterUserDTO(
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String
)
