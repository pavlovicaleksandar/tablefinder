package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.service.Restaurant
import com.pavlovicaleksandar.tablefinder.service.RestaurantService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = ["http://localhost:3000"])
class RestaurantController(private val service: RestaurantService) {
    @GetMapping
    fun getAllRestaurants(): List<RestaurantResponseDTO> {
        return service.findAll().toRestaurantResponseDTO()
    }

    @GetMapping("/{restaurantId}")
    fun getRestaurantById(@PathVariable restaurantId: UUID): RestaurantResponseDTO? {
        val restaurant = service.findById(restaurantId)
        if (restaurant != null) {
            return restaurant.toRestaurantResponseDTO()
        }
        // TODO handle not found case
        return null
    }
}

data class RestaurantResponseDTO(val id: UUID, val name: String, val description: String)

private fun List<Restaurant>.toRestaurantResponseDTO(): List<RestaurantResponseDTO> {
    return map {
        it.toRestaurantResponseDTO()
    }
}

private fun Restaurant.toRestaurantResponseDTO(): RestaurantResponseDTO {
    return RestaurantResponseDTO(
        id = this.id,
        name = this.name,
        description = this.description
    )
}
