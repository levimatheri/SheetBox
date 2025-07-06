package com.example.sheetbox.data.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class Converters {
    @TypeConverter
    fun fromTagsList(tags: List<String>): String = tags.joinToString(separator = ",")

    @TypeConverter
    fun toTagsList(tagsString: String): List<String> = tagsString.split(",").filter { it.isNotBlank() }

    @TypeConverter
    fun fromUUID(uuid: Uuid): String = uuid.toString()

    @TypeConverter
    fun toUUID(uuid: String): Uuid = Uuid.parse(uuid)

    @TypeConverter
    fun fromDate(date: LocalDateTime): String = date.toString()

    @TypeConverter
    fun toDate(date: String): LocalDateTime = LocalDateTime.parse(date)
}