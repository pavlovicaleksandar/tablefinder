package com.pavlovicaleksandar.tablefinder.service

import com.pavlovicaleksandar.tablefinder.repository.TableRecord
import com.pavlovicaleksandar.tablefinder.repository.TableRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TableService(val repository: TableRepository) {
    fun findAllTables(): List<TableRecord> = repository.findAll().toList()

    fun <T : Any> Optional<out T>.toList(): List<T> =
        if (isPresent) listOf(get()) else emptyList()
}
