package com.pavlovicaleksandar.tablefinder.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun save(user: UserRecord): Int {
        return jdbcTemplate.update(
            "insert into users(id, email, password, role) values(:id, :email, :password, :role)",
            mapOf(
                "id" to user.id,
                "email" to user.email,
                "password" to user.password,
                "role" to user.role
            )
        )
    }

    fun findByEmailAndPassword(email: String, password: String): UserRecord? {
        return jdbcTemplate.query(
            "select * from users where email = :email AND password = :password",
            mapOf(
                "email" to email,
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

    private val rowMapper = RowMapper<UserRecord> { resultSet, _ ->
        with(resultSet) {
            UserRecord(
                id = getUUID("id"),
                email = getString("email"),
                password = getString("password"),
                role = getString("role")
            )
        }
    }
}

data class UserRecord(
    val id: UUID,
    val email: String,
    val password: String,
    val role: String
)
