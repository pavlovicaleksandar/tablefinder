package com.pavlovicaleksandar.tablefinder.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class RestaurantRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findAll(): List<RestaurantRecord> {
        return jdbcTemplate.query(
            "select * from restaurants",
            rowMapper
        )
    }

    fun findById(id: UUID): RestaurantRecord? {
        return jdbcTemplate.query(
            "select * from restaurants where id = :id",
            mapOf(
                "id" to id
            ),
            rowMapper
        ).firstOrNull()
    }

    fun deleteById(restaurantId: UUID) {
        jdbcTemplate.update(
            "DELETE FROM restaurants WHERE id = :id",
            mapOf("id" to restaurantId)
        )
    }

    private val rowMapper = RowMapper<RestaurantRecord> { resultSet, _ ->
        with(resultSet) {
            RestaurantRecord(
                id = getUUID("id"),
                name = getString("name"),
                description = getString("description")
            )
        }
    }
}

data class RestaurantRecord(
    val id: UUID,
    val name: String,
    val description: String
)
