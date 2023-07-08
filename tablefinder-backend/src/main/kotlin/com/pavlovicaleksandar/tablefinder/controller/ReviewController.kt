package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.repository.ReviewRecord
import com.pavlovicaleksandar.tablefinder.service.Restaurant
import com.pavlovicaleksandar.tablefinder.service.ReviewService
import java.util.UUID
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.noContent
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = ["http://localhost:3000"])
class ReviewController(private val service: ReviewService) {

    @GetMapping("{restaurantId}")
    fun getReviewsByRestaurantId(@PathVariable restaurantId: UUID): List<ReviewResponseDTO> {
        return service.getReviewsByRestaurantId(restaurantId).toReviewResponseDTO()
    }

    @PostMapping
    fun createRestaurant(@RequestBody createReviewDTO: CreateReviewDTO): ResponseEntity<Unit> {
        service.createReview(createReviewDTO)
        return noContent().build()
    }
}

data class CreateReviewDTO(val username: String, val comment: String, val restaurantId: UUID)

data class ReviewResponseDTO(
    val id: UUID,
    val createdAt: Long,
    val username: String,
    val restaurantId: UUID,
    val comment: String
)

private fun List<ReviewRecord>.toReviewResponseDTO(): List<ReviewResponseDTO> {
    return map {
        it.toReviewResponseDTO()
    }
}

private fun ReviewRecord.toReviewResponseDTO(): ReviewResponseDTO {
    return ReviewResponseDTO(
        id = this.id,
        createdAt = this.createdAt,
        username = this.username,
        restaurantId = this.restaurantId,
        comment = this.comment
    )
}