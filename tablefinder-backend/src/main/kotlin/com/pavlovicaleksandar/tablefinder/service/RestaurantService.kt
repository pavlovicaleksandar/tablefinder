package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.repository.RestaurantRecord
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RestaurantService(private val repository: RestaurantRepository) {
    fun findAll(): List<Restaurant> {
        return repository.findAll().toRestaurantList()
    }
}

data class Restaurant(
    val id: UUID,
    val name: String,
    val description: String
)

private fun List<RestaurantRecord>.toRestaurantList(): List<Restaurant> {
    return map {
        Restaurant(
            id = it.id,
            name = it.name,
            description = it.description
        )
    }
}
