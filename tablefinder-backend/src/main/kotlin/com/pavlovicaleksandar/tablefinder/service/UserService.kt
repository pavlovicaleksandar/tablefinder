package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.RegisterUserDTO
import com.pavlovicaleksandar.tablefinder.repository.UserRecord
import com.pavlovicaleksandar.tablefinder.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class UserService(private val repository: UserRepository) {
    fun createUser(registerUserDTO: RegisterUserDTO): Int {
        val user = registerUserDTO.toUser()
        return repository.save(user.toRecord())
    }

    fun getUserById(id: UUID): User? {
        return repository.findById(id)?.toUser()
    }

    fun getAllUsers(): List<User> {
        return repository.findAll().toUserList()
    }

    fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return repository.findByUsernameAndPassword(
            username,
            hashPassword(password)
        )?.toUser()
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val role: String
)

private fun UserRecord.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        password = password,
        role = role
    )
}

private fun List<UserRecord>.toUserList(): List<User> {
    return map {
        it.toUser()
    }
}

fun RegisterUserDTO.toUser(): User {
    return User(
        id = UUID.randomUUID(),
        username = username,
        email = email,
        password = hashPassword(password),
        role = "GUEST"
    )
}

fun User.toRecord(): UserRecord = UserRecord(
    id = id,
    username = username,
    email = email,
    password = password,
    role = role
)
