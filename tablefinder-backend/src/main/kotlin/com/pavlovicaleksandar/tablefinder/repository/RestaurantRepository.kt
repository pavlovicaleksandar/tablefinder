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

    fun createRestaurant(record: RestaurantRecord): RestaurantRecord {
        jdbcTemplate.update(
            """INSERT INTO
                 restaurants(id, name, description, image_url) 
                 values(:id, :name, :description, :image_url)
            """.trimMargin(),
            mapOf(
                "id" to record.id,
                "name" to record.name,
                "description" to record.description,
                "image_url" to record.imageUrl
            )
        )
        return record
    }

    private val rowMapper = RowMapper<RestaurantRecord> { resultSet, _ ->
        with(resultSet) {
            RestaurantRecord(
                id = getUUID("id"),
                name = getString("name"),
                description = getString("description"),
                imageUrl = getString("image_url")
            )
        }
    }
}

data class RestaurantRecord(
    val id: UUID,
    val name: String,
    val description: String,
    val imageUrl: String
)
