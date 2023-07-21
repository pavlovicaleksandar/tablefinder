package com.pavlovicaleksandar.tablefinder.repository

import java.util.UUID
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TagRepository(private val jdbcTemplate: NamedParameterJdbcTemplate) {
    fun findAll(): List<TagRecord> {
        return jdbcTemplate.query(
            "select * from tags",
            tagRowMapper
        )
    }

    fun findById(id: UUID): List<TagRecord> {
        return jdbcTemplate.query(
            "select * from tags where id = :id",
            mapOf(
                "id" to id
            ),
            tagRowMapper
        )
    }

    fun linkToRestaurant(restaurantId: UUID, tagRecord: TagRecord) {
        jdbcTemplate.update(
            """
                INSERT INTO linked_tags(restaurant_id, tag_id, tag_name) 
                            values(:restaurant_id, :tag_id, :tag_name)
            """.trimMargin(),
            mapOf(
                "restaurant_id" to restaurantId,
                "tag_id" to tagRecord.id,
                "tag_name" to tagRecord.name,
            )
        )
    }

    fun getLinkedTagsFor(restaurantId: UUID): MutableList<LinkedTagRecord> {
        return jdbcTemplate.query(
            """
                select * from linked_tags as lt, tags as t 
                where lt.restaurant_id = :restaurant_id 
                and lt.tag_id = t.id
            """.trimIndent(),
            mapOf(
                "restaurant_id" to restaurantId
            ),
            linkedTagRowMapper
        )
    }

    private val linkedTagRowMapper = RowMapper<LinkedTagRecord> { resultSet, _ ->
        with(resultSet) {
            LinkedTagRecord(
                restaurantId = getUUID("restaurant_id"),
                tagId = getUUID("tag_id"),
                tagName = getString("name")
            )
        }
    }

    private val tagRowMapper = RowMapper<TagRecord> { resultSet, _ ->
        with(resultSet) {
            TagRecord(
                id = getUUID("id"),
                name = getString("name"),
            )
        }
    }
}

data class TagRecord(val id: UUID, val name: String)

data class LinkedTagRecord(val restaurantId: UUID, val tagId: UUID, val tagName: String)