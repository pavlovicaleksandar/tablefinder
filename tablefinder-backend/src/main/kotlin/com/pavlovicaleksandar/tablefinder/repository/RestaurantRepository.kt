package com.pavlovicaleksandar.tablefinder.repository

import java.util.UUID
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

data class RestaurantRecord(
    val id: UUID,
    val name: String,
    val description: String
)

@Repository
class RestaurantRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findAll(): List<RestaurantRecord> {
        return jdbcTemplate.query(
            "select * from restaurants",
            rowMapper
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
