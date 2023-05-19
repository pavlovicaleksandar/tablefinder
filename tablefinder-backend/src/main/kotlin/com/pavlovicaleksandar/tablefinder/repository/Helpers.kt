package com.pavlovicaleksandar.tablefinder.repository

import java.sql.ResultSet
import java.util.UUID

fun ResultSet.getUUID(columnName: String): UUID = UUID.fromString(getString(columnName))
