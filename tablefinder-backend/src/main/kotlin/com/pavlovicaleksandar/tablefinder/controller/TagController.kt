package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.repository.TagRecord
import com.pavlovicaleksandar.tablefinder.service.TagService
import com.pavlovicaleksandar.tablefinder.service.UserInfo
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = ["http://localhost:3000"])
class TagController(private val service: TagService) {
    @GetMapping
    fun getAll(): ResponseEntity<List<TagRecord>> {
        return ok(service.findAll())
    }
}
fun Authentication.toUser(): UserInfo {
    return principal as UserInfo
}