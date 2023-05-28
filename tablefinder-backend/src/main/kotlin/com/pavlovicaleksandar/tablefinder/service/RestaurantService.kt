package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateRestaurantDTO
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRecord
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RestaurantService(private val repository: RestaurantRepository) {
    fun findAll(): List<Restaurant> {
        return repository.findAll().toRestaurantList()
    }

    fun findById(restaurantId: UUID): Restaurant? {
        return repository.findById(restaurantId)?.toRestaurant()
    }

    fun deleteById(restaurantId: UUID) {
        return repository.deleteById(restaurantId)
    }

    fun createRestaurant(dto: CreateRestaurantDTO): Restaurant {
        return repository.createRestaurant(dto.toRestaurantRecord()).toRestaurant()
    }
}

fun CreateRestaurantDTO.toRestaurantRecord(): RestaurantRecord {
    return RestaurantRecord(
        id = UUID.randomUUID(),
        name = this.name,
        description = this.description
    )
}
private fun RestaurantRecord.toRestaurant(): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        description = this.description
    )
}

data class Restaurant(
    val id: UUID,
    val name: String,
    val description: String
)

private fun List<RestaurantRecord>.toRestaurantList(): List<Restaurant> {
    return map {
        it.toRestaurant()
    }
}
