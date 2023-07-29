package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.repository.LinkedTagRecord
import com.pavlovicaleksandar.tablefinder.repository.TagRecord
import com.pavlovicaleksandar.tablefinder.service.Restaurant
import com.pavlovicaleksandar.tablefinder.service.RestaurantService
import com.pavlovicaleksandar.tablefinder.service.Role.Guest
import com.pavlovicaleksandar.tablefinder.service.UserInfo
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import java.util.UUID.randomUUID
import kotlin.math.roundToInt
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = ["http://localhost:3000"])
class RestaurantController(private val service: RestaurantService) {
    val NOT_LOGGED_IN_USER = UserInfo(randomUUID(), "not-logged-user", Guest)

    @PostMapping
    fun createRestaurant(@RequestBody createRestaurantDTO: CreateRestaurantDTO): RestaurantResponseDTO {
        return service.createRestaurant(createRestaurantDTO).toRestaurantResponseDTO()
    }

    @PutMapping("{restaurantId}")
    fun updateRestaurant(@RequestBody updateRestaurantDTO: UpdateRestaurantDTO, @PathVariable restaurantId: UUID): ResponseEntity<Unit> {
        service.updateRestaurant(restaurantId, updateRestaurantDTO)
        return ResponseEntity.ok().build()
    }

    @GetMapping
    fun getAllRestaurants(@RequestParam(defaultValue = "0") ratingFilter: Int, @RequestParam(defaultValue = "0") priceFilter: Int, authentication: Authentication?): List<RestaurantResponseDTO> {

        return service.findAll(ratingFilter, priceFilter, authentication?.toUser() ?: NOT_LOGGED_IN_USER).toRestaurantResponseDTO()
    }

    @GetMapping("{restaurantId}")
    fun getRestaurantById(@PathVariable restaurantId: UUID): RestaurantResponseDTO? {
        val restaurant = service.findById(restaurantId)
        if (restaurant != null) {
            return restaurant.toRestaurantResponseDTO()
        }
        return null
    }

    @DeleteMapping("/{restaurantId}")
    fun deleteRestaurantById(@PathVariable restaurantId: UUID): ResponseEntity<Any> {
        service.deleteById(restaurantId)
        return noContent().build()
    }
}

data class UpdateRestaurantDTO(
    val name: String,
    val description: String,
    val imageUrl: String,
    val tags: List<TagRecord>,
    val managerUsername: String
)

data class CreateRestaurantDTO(
    val name: String,
    val description: String,
    val imageUrl: String,
    val tags: List<TagRecord>,
    val managerUsername: String
)

data class RestaurantResponseDTO(
    val id: UUID,
    val name: String,
    val description: String,
    val imageUrl: String,
    val numberOfPrices: Int,
    val numberOfRatings: Int,
    val pricesSum: Int,
    val ratingsSum: Int,
    val rating: Double,
    val price: Int,
    val tags: List<LinkedTagRecord>,
    val managerUsername: String
)

private fun List<Restaurant>.toRestaurantResponseDTO(): List<RestaurantResponseDTO> {
    return map {
        it.toRestaurantResponseDTO()
    }
}

private fun Restaurant.toRestaurantResponseDTO(): RestaurantResponseDTO {
    return RestaurantResponseDTO(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        ratingsSum = this.ratingsSum,
        pricesSum = this.pricesSum,
        numberOfRatings = this.numberOfRatings,
        numberOfPrices = this.numberOfPrices,
        rating = if (this.numberOfRatings != 0) this.ratingsSum.div(this.numberOfRatings.toDouble()) else 0.0,
        price = (if (this.numberOfPrices != 0) this.pricesSum.div(this.numberOfPrices.toDouble()) else 0.0).roundToInt(),
        tags = this.tags,
        managerUsername = managerUsername
    )
}
