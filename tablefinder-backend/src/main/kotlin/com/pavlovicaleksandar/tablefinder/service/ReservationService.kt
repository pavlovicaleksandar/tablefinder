package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateReservationDTO
import com.pavlovicaleksandar.tablefinder.controller.ReservationStatus
import com.pavlovicaleksandar.tablefinder.repository.ReservationRecord
import com.pavlovicaleksandar.tablefinder.repository.ReservationRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class ReservationService(val repository: ReservationRepository) {
    fun findAllReservations(): List<Reservation> = repository.findAll().toReservationList()

    fun createReservation(createReservationDTO: CreateReservationDTO): Int {
        return repository.createReservation(
            createReservationDTO.userId,
            createReservationDTO.restaurantId,
            createReservationDTO.dateAndTime,
            createReservationDTO.numberOfPeople,
            createReservationDTO.noteForRestaurant
        )
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}

data class Reservation(
    val id: UUID,
    val dateAndTime: Long,
    val numberOfPeople: Int,
    val userId: UUID,
    val restaurantId: UUID,
    val noteForRestaurant: String,
    val status: ReservationStatus
)

private fun ReservationRecord.toReservation(): Reservation {
    return Reservation(
        id = this.id,
        dateAndTime = this.dateAndTime,
        numberOfPeople = this.numberOfPeople,
        userId = this.userId,
        restaurantId = this.restaurantId,
        noteForRestaurant = this.noteForRestaurant,
        status = this.status
    )
}

private fun List<ReservationRecord>.toReservationList(): List<Reservation> {
    return map {
        it.toReservation()
    }
}
