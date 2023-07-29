package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateReservationDTO
import com.pavlovicaleksandar.tablefinder.controller.ReservationStatus
import com.pavlovicaleksandar.tablefinder.repository.ReservationRecord
import com.pavlovicaleksandar.tablefinder.repository.ReservationRepository
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRepository
import com.pavlovicaleksandar.tablefinder.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID

@Service
class ReservationService(
    val reservationRepository: ReservationRepository,
    val userRepository: UserRepository,
    val restaurantRepository: RestaurantRepository
) {
    fun findAllReservationsFor(userInfo: UserInfo): List<Reservation> {
        return when (userInfo.role) {
            Role.Admin -> {
                reservationRepository.findAll().map {
                    val user = userRepository.findById(it.userId)!!
                    val restaurant = restaurantRepository.findById(it.restaurantId)!!
                    it.toReservation(user.username, restaurant.name)
                }
            }
            Role.Manager -> {
                reservationRepository.findAll().filter {
                    val restaurant = restaurantRepository.findById(it.restaurantId)!!
                    restaurant.managerUsername ==userInfo.username
                }.map {
                    val user = userRepository.findById(it.userId)
                    val restaurant = restaurantRepository.findById(it.restaurantId)
                    it.toReservation(user!!.username, restaurant!!.name)
                }
            }
            Role.Guest -> {
                return reservationRepository.findAll().filter {
                  it.userId == userInfo.userId
                }.map {
                    val user = userRepository.findById(it.userId)
                    val restaurant = restaurantRepository.findById(it.restaurantId)
                    it.toReservation(user!!.username, restaurant!!.name)
                }
            }
        }
    }

    fun createReservation(createReservationDTO: CreateReservationDTO): Int {
        return reservationRepository.createReservation(
            createReservationDTO.userId,
            createReservationDTO.restaurantId,
            createReservationDTO.dateAndTime,
            createReservationDTO.numberOfPeople,
            createReservationDTO.noteForRestaurant
        )
    }

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()

    fun changeReservationStatus(reservationId: UUID, noteFromRestaurant: String?, status: ReservationStatus) {
        reservationRepository.changeReservationStatus(reservationId, noteFromRestaurant, status)
    }
}

data class Reservation(
    val id: UUID,
    val dateAndTime: Long,
    val numberOfPeople: Int,
    val userId: UUID,
    val username: String,
    val restaurantId: UUID,
    val restaurantName: String,
    val noteForRestaurant: String,
    val noteFromRestaurant: String,
    val status: ReservationStatus
)

private fun ReservationRecord.toReservation(username: String, restaurantName: String): Reservation {
    return Reservation(
        id = this.id,
        dateAndTime = this.dateAndTime,
        numberOfPeople = this.numberOfPeople,
        userId = this.userId,
        username = username,
        restaurantId = this.restaurantId,
        restaurantName = restaurantName,
        noteForRestaurant = this.noteForRestaurant,
        noteFromRestaurant = this.noteFromRestaurant,
        status = this.status
    )
}

