package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.service.User
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
import org.springframework.web.bind.annotation.CrossOrigin

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["http://localhost:3000"])
class UserController(private val service: UserService) {

    @PostMapping
    fun createUser(@RequestBody user: CreateUserDTO): ResponseEntity<Int> {
        val success = service.createUser(user)
        return ResponseEntity(success, HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody login: LoginDTO): ResponseEntity<User> {
        val user = service.getUserByEmailAndPassword(login.email, login.password)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
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
}

data class LoginDTO(val email: String, val password: String)

data class CreateUserDTO(
    val id: UUID,
    val email: String,
    val password: String,
    val role: String
)
