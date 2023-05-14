package com.pavlovicaleksandar.tablefinder.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Restaurant(val name: String, val description: String)

@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = ["http://localhost:3000"])
class RestaurantController() {
    @GetMapping
    fun getAllRestaurants(): List<Restaurant> {
        return listOf(Restaurant(name = "Restaurant 1", description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit."))
    }
}
