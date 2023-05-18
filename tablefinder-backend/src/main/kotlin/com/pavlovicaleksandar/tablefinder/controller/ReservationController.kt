package com.pavlovicaleksandar.tablefinder.controller

import java.util.UUID
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = ["http://localhost:3000"])
class ReservationController {

    @PostMapping
    fun createReservation(@RequestBody createReservationDTO: CreateReservationDTO): CreateReservationDTO {
        return createReservationDTO
    }
}

data class CreateReservationDTO(val restaurantId: UUID, val userId: UUID, val numberOfPeople: Int)
