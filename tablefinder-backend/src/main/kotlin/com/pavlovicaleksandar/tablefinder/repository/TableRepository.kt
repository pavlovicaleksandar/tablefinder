package com.pavlovicaleksandar.tablefinder.repository

import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class TableRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findAll(): List<TableRecord> {
        return jdbcTemplate.query(
            "select * from tables",
            rowMapper
        )
    }

    private val rowMapper = RowMapper<TableRecord> { resultSet, _ ->
        with(resultSet) {
            TableRecord(
                id = getUUID("id")
            )
        }
    }
}

data class TableRecord(val id: UUID)
