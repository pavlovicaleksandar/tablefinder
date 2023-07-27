package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.RegisterUserDTO
import com.pavlovicaleksandar.tablefinder.controller.UpdateUserDTO
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

    fun checkIsEmailOrUsernameTaken(username: String, email: String): String? {
        if (repository.findByUsername(username) != null) {
            return "Username already taken"
        }
        if (repository.findByEmail(email) != null) {
            return "Email already taken"
        }
        return null
    }

    fun getUserById(id: UUID): User? {
        return repository.findById(id)?.toUser()
    }

    fun getAllUsers(): List<User> {
        return repository.findAll().toUserList()
    }
    fun getAllModerators(): List<User> {
        return repository.findAllModerators().toUserList()
    }

    fun getUserByUsernameAndPassword(username: String, password: String): User? {
        return repository.findByUsernameAndPassword(
            username,
            hashPassword(password)
        )?.toUser()
    }

    fun deleteByUsername(username: String) {
        repository.deleteByUsername(username)
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()

    fun updateUser(userId: UUID, updateUserDTO: UpdateUserDTO) {
        val userToBeUpdated = repository.findById(userId)!!
        val userRecord = userToBeUpdated.copy(
            email = updateUserDTO.email ?: userToBeUpdated.email,
            role = updateUserDTO.role?.name ?: userToBeUpdated.role,
            phoneNumber = updateUserDTO.phoneNumber ?: userToBeUpdated.phoneNumber,
            password = if (updateUserDTO.password != null)
                hashPassword(updateUserDTO.password) else userToBeUpdated.password
        )
        repository.updateUser(userRecord)
    }
}

enum class Role {
    Guest,
    Moderator,
    Admin
}

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val role: Role,
    val phoneNumber: String
)

private fun UserRecord.toUser(): User {
    return User(
        id = id,
        username = username,
        email = email,
        password = password,
        role = Role.valueOf(role),
        phoneNumber = phoneNumber
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
        role = Role.Guest,
        phoneNumber = phoneNumber
    )
}

fun User.toRecord(): UserRecord = UserRecord(
    id = id,
    username = username,
    email = email,
    password = password,
    role = role.name,
    phoneNumber = phoneNumber
)
