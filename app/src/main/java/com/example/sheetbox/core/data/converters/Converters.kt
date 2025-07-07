package com.example.sheetbox.core.data.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.util.UUID

class Converters {
    @TypeConverter
    fun fromTagsList(tags: List<String>): String = tags.joinToString(separator = ",")

    @TypeConverter
    fun toTagsList(tagsString: String): List<String> = tagsString.split(",").filter { it.isNotBlank() }

    @TypeConverter
    fun fromUUID(uuid: UUID): String = uuid.toString()

    @TypeConverter
    fun toUUID(uuid: String): UUID = UUID.fromString(uuid)

    @TypeConverter
    fun fromDate(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun toDate(date: String): LocalDateTime = LocalDateTime.parse(date)
}