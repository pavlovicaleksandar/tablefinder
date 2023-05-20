package com.pavlovicaleksandar.tablefinder.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID
import java.util.UUID.randomUUID

@Repository
class ReservationRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findAll(): List<ReservationRecord> {
        return jdbcTemplate.query(
            "select * from reservations",
            rowMapper
        )
    }

    fun createReservation(userId: UUID, restaurantId: UUID, numberOfPeople: Int, noteForRestaurant: String): Int {
        return jdbcTemplate.update(
            "insert into reservations(id, user_id, restaurant_id, number_of_people, note_for_restaurant) values(:id, :user_id, :restaurant_id, :number_of_people, :note_for_restaurant)",
            mapOf(
                "id" to randomUUID(),
                "user_id" to userId,
                "restaurant_id" to restaurantId,
                "number_of_people" to numberOfPeople,
                "note_for_restaurant" to noteForRestaurant
            )
        )
    }

    private val rowMapper = RowMapper<ReservationRecord> { resultSet, _ ->
        with(resultSet) {
            ReservationRecord(
                id = getUUID("id"),
                userId = getUUID("user_id"),
                restaurantId = getUUID("restaurant_id"),
                numberOfPeople = getInt("number_of_people"),
                noteForRestaurant = getString("note_for_restaurant")
            )
        }
    }
}

data class ReservationRecord(
    val id: UUID,
    val numberOfPeople: Int,
    val userId: UUID,
    val restaurantId: UUID,
    val noteForRestaurant: String
)

