package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.controller.CreateReviewDTO
import com.pavlovicaleksandar.tablefinder.repository.ReviewRecord
import com.pavlovicaleksandar.tablefinder.repository.ReviewRepository
import java.time.Instant.now
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class ReviewService(private val repository: ReviewRepository) {
    fun createReview(dto: CreateReviewDTO) {
       repository.createReview(dto.toReviewRecord())
    }

    fun getReviewsByRestaurantId(restaurantId: UUID): List<ReviewRecord> {
        return repository.getByRestaurantId(restaurantId)
    }
}

fun CreateReviewDTO.toReviewRecord(): ReviewRecord {
    return ReviewRecord(
        id = UUID.randomUUID(),
        username = this.username,
        comment = this.comment,
        createdAt = now().toEpochMilli(),
        restaurantId = this.restaurantId,
        rating = this.rating,
        price = this.price
    )
}


