package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.repository.TagRecord
import com.pavlovicaleksandar.tablefinder.repository.TagRepository
import org.springframework.stereotype.Service

@Service
class TagService(private val repository: TagRepository) {
    fun findAll(): List<TagRecord> {
        return repository.findAll()
    }
}
