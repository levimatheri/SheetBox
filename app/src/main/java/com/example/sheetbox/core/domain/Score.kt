package com.example.sheetbox.core.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "scores")
data class Score(
    @PrimaryKey() val id: UUID = UUID.randomUUID(),
    val title: String,
    val composer: String? = null,
    val tags: List<String> = emptyList(), // Serialize with a type converter
    val filePath: String,
    val pageCount: Int,
    val lastOpened: LocalDateTime? = null,
    val addedDate: LocalDateTime = LocalDateTime.now()
)