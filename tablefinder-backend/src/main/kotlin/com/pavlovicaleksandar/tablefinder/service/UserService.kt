package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateUserDTO
import com.pavlovicaleksandar.tablefinder.repository.UserRecord
import com.pavlovicaleksandar.tablefinder.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class UserService(private val repository: UserRepository) {
    fun createUser(createUserDTO: CreateUserDTO): Int {
        val user = createUserDTO.toUser()
        return repository.save(user.toRecord())
    }

    fun getUserById(id: UUID): User? {
        return repository.findById(id)?.toUser()
    }

    fun getAllUsers(): List<User> {
        return repository.findAll().toUserList()
    }

    fun getUserByEmailAndPassword(email: String, password: String): User? {
        return repository.findByEmailAndPassword(
            email,
            hashPassword(password)
        )?.toUser()
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}

data class User(
    val id: UUID,
    val email: String,
    val password: String,
    val role: String
)

private fun UserRecord.toUser(): User {
    return User(
        id = id,
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

fun CreateUserDTO.toUser(): User {
    return User(
        id = UUID.randomUUID(),
        email = email,
        password = hashPassword(password),
        role = role
    )
}

fun User.toRecord(): UserRecord = UserRecord(
    id = id,
    email = email,
    password = password,
    role = role
)
