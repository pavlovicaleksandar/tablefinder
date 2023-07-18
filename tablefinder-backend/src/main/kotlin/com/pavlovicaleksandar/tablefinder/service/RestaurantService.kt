package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateRestaurantDTO
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRecord
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.math.roundToInt

@Service
class RestaurantService(private val repository: RestaurantRepository) {
    fun findAll(ratingFilter: Int, priceFilter: Int): List<Restaurant> {
        return repository.findAll().toRestaurantList().filter {
            val rating = if (it.numberOfRatings != 0) it.ratingsSum.div(it.numberOfRatings.toDouble()) else 0.0
            val price = if (it.numberOfPrices != 0) it.pricesSum.div(it.numberOfPrices.toDouble()) else 0.0

            rating >= ratingFilter && (price.roundToInt() == priceFilter || priceFilter == 0)
        }
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
        description = this.description,
        imageUrl = this.imageUrl,
        ratingsSum = 0,
        numberOfPrices = 0,
        pricesSum = 0,
        numberOfRatings = 0
    )
}
private fun RestaurantRecord.toRestaurant(): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        pricesSum = this.pricesSum,
        ratingsSum = this.ratingsSum,
        numberOfPrices = this.numberOfPrices,
        numberOfRatings = this.numberOfRatings
    )
}

data class Restaurant(
    val id: UUID,
    val name: String,
    val description: String,
    val imageUrl: String,
    val numberOfRatings: Int,
    val numberOfPrices: Int,
    val ratingsSum: Int,
    val pricesSum: Int,
)

private fun List<RestaurantRecord>.toRestaurantList(): List<Restaurant> {
    return map {
        it.toRestaurant()
    }
}
