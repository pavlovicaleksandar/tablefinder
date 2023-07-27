package com.pavlovicaleksandar.tablefinder.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun save(user: UserRecord): Int {
        return jdbcTemplate.update(
            "insert into users(id, username, email, password, role, phone_number) values(:id, :username, :email, :password, :role, :phone_number)",
            mapOf(
                "id" to user.id,
                "username" to user.username,
                "email" to user.email,
                "password" to user.password,
                "role" to user.role,
                "phone_number" to user.phoneNumber
            )
        )
    }
    fun findByUsername(username: String): UserRecord? {
        return jdbcTemplate.query(
            "select * from users where username = :username",
            mapOf(
                "username" to username,
            ),
            rowMapper
        ).firstOrNull()
    }
    fun findByEmail(email: String): UserRecord? {
        return jdbcTemplate.query(
            "select * from users where email = :email",
            mapOf(
                "email" to email,
            ),
            rowMapper
        ).firstOrNull()
    }

    fun findByUsernameAndPassword(username: String, password: String): UserRecord? {
        return jdbcTemplate.query(
            "select * from users where username = :username AND password = :password",
            mapOf(
                "username" to username,
                "password" to password
            ),
            rowMapper
        ).firstOrNull()
    }

    fun findById(id: UUID): UserRecord? {
        return jdbcTemplate.query(
            "select * from users where id = :id",
            mapOf(
                "id" to id
            ),
            rowMapper
        ).firstOrNull()
    }

    fun findAll(): List<UserRecord> {
        return jdbcTemplate.query(
            "select * from users",
            rowMapper
        )
    }
    fun findAllModerators(): List<UserRecord> {
        return jdbcTemplate.query(
            "select * from users where role = 'Moderator'",
            rowMapper
        )
    }

    fun deleteByUsername(username: String) {
        jdbcTemplate.update(
            "DELETE FROM users WHERE username = :username",
            mapOf("username" to username)
        )
    }

    fun updateUser(userRecord: UserRecord) {
        jdbcTemplate.update(
            """
                UPDATE users 
                SET email = :email, role = :role, phone_number = :phone_number, password = :password
                WHERE id = :id
            """.trimIndent(),
            mapOf(
                "id" to userRecord.id,
                "email" to userRecord.email,
                "role" to userRecord.role,
                "phone_number" to userRecord.phoneNumber,
                "password" to userRecord.password
            )
        )
    }

    private val rowMapper = RowMapper<UserRecord> { resultSet, _ ->
        with(resultSet) {
            UserRecord(
                id = getUUID("id"),
                username = getString("username"),
                email = getString("email"),
                password = getString("password"),
                role = getString("role"),
                phoneNumber = getString("phone_number")
            )
        }
    }
}

data class UserRecord(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val role: String,
    val phoneNumber: String
)
