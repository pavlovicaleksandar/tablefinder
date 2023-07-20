package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateRestaurantDTO
import com.pavlovicaleksandar.tablefinder.repository.LinkedTagRecord
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRecord
import com.pavlovicaleksandar.tablefinder.repository.RestaurantRepository
import com.pavlovicaleksandar.tablefinder.repository.TagRepository
import org.springframework.stereotype.Service
import java.util.UUID
import kotlin.math.roundToInt

@Service
class RestaurantService(private val repository: RestaurantRepository, private val tagRepository: TagRepository) {
    fun findAll(ratingFilter: Int, priceFilter: Int): List<Restaurant> {
        val restaurantRecords = repository.findAll().filter {
            val rating = if (it.numberOfRatings != 0) it.ratingsSum.div(it.numberOfRatings.toDouble()) else 0.0
            val price = if (it.numberOfPrices != 0) it.pricesSum.div(it.numberOfPrices.toDouble()) else 0.0

            rating >= ratingFilter && (price.roundToInt() == priceFilter || priceFilter == 0)
        }

        return restaurantRecords.map {
            val linkedTags = tagRepository.getLinkedTagsFor(it.id)
            it.toRestaurant(linkedTags)
        }
    }

    fun findById(restaurantId: UUID): Restaurant? {
        val linkedTags = tagRepository.getLinkedTagsFor(restaurantId)
        return repository.findById(restaurantId)?.toRestaurant(linkedTags)
    }

    fun deleteById(restaurantId: UUID) {
        return repository.deleteById(restaurantId)
    }

    fun createRestaurant(dto: CreateRestaurantDTO): Restaurant {
        return repository.createRestaurant(dto.toRestaurantRecord()).toRestaurant(emptyList<LinkedTagRecord>().toMutableList())
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
private fun RestaurantRecord.toRestaurant(linkedTags: MutableList<LinkedTagRecord>): Restaurant {
    return Restaurant(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl,
        pricesSum = this.pricesSum,
        ratingsSum = this.ratingsSum,
        numberOfPrices = this.numberOfPrices,
        numberOfRatings = this.numberOfRatings,
        tags = linkedTags.toList()
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
    val tags: List<LinkedTagRecord>
)

