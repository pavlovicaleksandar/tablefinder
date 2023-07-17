package com.pavlovicaleksandar.tablefinder.repository

import java.sql.Timestamp
import java.util.UUID
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ReviewRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun createReview(
        record: ReviewRecord
    ): Int {
        return jdbcTemplate.update(
            """
                insert into 
                    reviews(id, username, comment, restaurant_id, created_at, price, rating) 
                    values(:id, :username, :comment, :restaurant_id, :created_at, :price, :rating)"""
                .trimMargin(),
            mapOf(
                "id" to record.id,
                "username" to record.username,
                "created_at" to Timestamp(record.createdAt),
                "comment" to record.comment,
                "restaurant_id" to record.restaurantId,
                "price" to record.price,
                "rating" to record.rating
            )
        )
    }

    fun getByRestaurantId(restaurantId: UUID): List<ReviewRecord> {
        return jdbcTemplate.query(
            "select * from reviews where restaurant_id = :restaurant_id order by created_at desc ",
            mapOf(
                "restaurant_id" to restaurantId
            ),
            rowMapper
        )
    }

    private val rowMapper = RowMapper<ReviewRecord> { resultSet, _ ->
        with(resultSet) {
            ReviewRecord(
                id = getUUID("id"),
                username = getString("username"),
                createdAt = getTimestamp("created_at").time,
                restaurantId = getUUID("restaurant_id"),
                comment = getString("comment"),
                price = getInt("price"),
                rating = getInt("rating")
            )
        }
    }
}


data class ReviewRecord(
    val id: UUID,
    val createdAt: Long,
    val username: String,
    val restaurantId: UUID,
    val comment: String,
    val price: Int,
    val rating: Int
)