package com.pavlovicaleksandar.tablefinder.repository

import com.pavlovicaleksandar.tablefinder.controller.ReservationStatus
import java.sql.ResultSet
import java.sql.Timestamp
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

    fun createReservation(
        userId: UUID,
        restaurantId: UUID,
        dateAndTime: Long,
        numberOfPeople: Int,
        noteForRestaurant: String,
    ): Int {
        return jdbcTemplate.update(
            """
                insert into 
                    reservations(id, user_id, date_and_time, restaurant_id, number_of_people, note_for_restaurant, note_from_restaurant, status) 
                    values(:id, :user_id, :date_and_time, :restaurant_id, :number_of_people, :note_for_restaurant, :note_from_restaurant, :status)"""
                .trimMargin(),
            mapOf(
                "id" to randomUUID(),
                "user_id" to userId,
                "date_and_time" to Timestamp(dateAndTime),
                "restaurant_id" to restaurantId,
                "number_of_people" to numberOfPeople,
                "note_for_restaurant" to noteForRestaurant,
                "note_from_restaurant" to "",
                "status" to ReservationStatus.PENDING.name
            )
        )
    }

    fun changeReservationStatus(reservationId: UUID, noteFromRestaurant: String?, status: ReservationStatus) {
        jdbcTemplate.update(
            """
            UPDATE reservations
            SET status = :status, note_from_restaurant = :note_from_restaurant
            WHERE id = :reservation_id
        """.trimIndent(),
            mapOf(
                "reservation_id" to reservationId,
                "status" to status.name,
                "note_from_restaurant" to noteFromRestaurant
            )
        )
    }

    private val rowMapper = RowMapper<ReservationRecord> { resultSet, _ ->
        with(resultSet) {
            ReservationRecord(
                id = getUUID("id"),
                userId = getUUID("user_id"),
                dateAndTime = getTimestamp("date_and_time").time,
                restaurantId = getUUID("restaurant_id"),
                numberOfPeople = getInt("number_of_people"),
                noteForRestaurant = getString("note_for_restaurant"),
                noteFromRestaurant = getString("note_from_restaurant"),
                status = getReservationStatus("status")
            )
        }
    }
}

fun ResultSet.getReservationStatus(columnLabel: String): ReservationStatus {
    return ReservationStatus.valueOf(getString(columnLabel))
}
data class ReservationRecord(
    val id: UUID,
    val dateAndTime: Long,
    val numberOfPeople: Int,
    val userId: UUID,
    val restaurantId: UUID,
    val noteForRestaurant: String,
    val noteFromRestaurant: String,
    val status: ReservationStatus
)

