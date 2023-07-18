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
            """
                INSERT INTO restaurants(id, name, description, image_url, number_of_ratings, ratings_sum, number_of_prices, prices_sum) 
                            values(:id, :name, :description, :image_url, :number_of_ratings, :ratings_sum, :number_of_prices, :prices_sum)
            """.trimMargin(),
            mapOf(
                "id" to record.id,
                "name" to record.name,
                "description" to record.description,
                "image_url" to record.imageUrl,
                "number_of_ratings" to record.numberOfRatings,
                "ratings_sum" to record.ratingsSum,
                "number_of_prices" to record.numberOfPrices,
                "prices_sum" to record.pricesSum,
            )
        )
        return record
    }

    fun updateRestaurant(record: RestaurantRecord) {
        jdbcTemplate.update(
            """
                UPDATE restaurants 
                SET name = :name, description = :description, image_url = :image_url, number_of_ratings = :number_of_ratings, ratings_sum = :ratings_sum, number_of_prices = :number_of_prices, prices_sum = :prices_sum
                WHERE id = :id
            """.trimMargin(),
            mapOf(
                "id" to record.id,
                "name" to record.name,
                "description" to record.description,
                "image_url" to record.imageUrl,
                "number_of_ratings" to record.numberOfRatings,
                "ratings_sum" to record.ratingsSum,
                "number_of_prices" to record.numberOfPrices,
                "prices_sum" to record.pricesSum,
            )
        )
    }

    private val rowMapper = RowMapper<RestaurantRecord> { resultSet, _ ->
        with(resultSet) {
            RestaurantRecord(
                id = getUUID("id"),
                name = getString("name"),
                description = getString("description"),
                imageUrl = getString("image_url"),
                numberOfRatings = getInt("number_of_ratings"),
                ratingsSum = getInt("ratings_sum"),
                numberOfPrices = getInt("number_of_prices"),
                pricesSum = getInt("prices_sum")
            )
        }
    }
}

data class RestaurantRecord(
    val id: UUID,
    val name: String,
    val description: String,
    val imageUrl: String,
    val numberOfRatings: Int,
    val ratingsSum: Int,
    val numberOfPrices: Int,
    val pricesSum: Int,
)
