package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.service.Reservation
import com.pavlovicaleksandar.tablefinder.service.ReservationService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.accepted
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = ["http://localhost:3000"])
class ReservationController(private val service: ReservationService) {

    @PostMapping
    fun createReservation(@RequestBody createReservationDTO: CreateReservationDTO): Int {
        return service.createReservation(createReservationDTO)
    }

    @GetMapping
    fun getAllReservations(): List<ReservationResponseDTO> {
        return service.findAllReservations().toReservationResponseDTO()
    }
    @PutMapping("/{reservationId}")
    fun changeReservationStatus(@PathVariable reservationId: UUID, @RequestBody dto: ChangeReservationStatusDTO): ResponseEntity<Any> {
        service.changeReservationStatus(reservationId, dto.noteFromRestaurant, dto.status)
        return accepted().build()
    }
}

data class ChangeReservationStatusDTO(
    val noteFromRestaurant: String?,
    val status: ReservationStatus
)

private fun List<Reservation>.toReservationResponseDTO(): List<ReservationResponseDTO> {
    return map {
        it.toReservationResponseDTO()
    }
}
private fun Reservation.toReservationResponseDTO(): ReservationResponseDTO {
    return ReservationResponseDTO(
        id = this.id,
        dateAndTime = this.dateAndTime,
        numberOfPeople = this.numberOfPeople,
        userId = this.userId,
        restaurantId = this.restaurantId,
        noteForRestaurant = this.noteForRestaurant,
        noteFromRestaurant = this.noteFromRestaurant,
        status = this.status
    )
}

data class ReservationResponseDTO(
    val id: UUID,
    val dateAndTime: Long,
    val numberOfPeople: Int,
    val userId: UUID,
    val restaurantId: UUID,
    val noteForRestaurant: String,
    val noteFromRestaurant: String,
    val status: ReservationStatus
)
data class CreateReservationDTO(
    val restaurantId: UUID,
    val userId: UUID,
    val dateAndTime: Long,
    val numberOfPeople: Int,
    val noteForRestaurant: String
)
