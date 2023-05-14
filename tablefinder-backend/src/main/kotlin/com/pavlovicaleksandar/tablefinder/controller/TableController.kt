package com.pavlovicaleksandar.tablefinder.controller

import com.pavlovicaleksandar.tablefinder.service.TableService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/table")
class TableController(val service: TableService) {
    @GetMapping
    fun index() = service.findAllTables()
}
