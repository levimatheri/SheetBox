package com.example.sheetbox.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Entity
data class Score(
    @PrimaryKey(autoGenerate = true) val id: Uuid,
    val title: String,
    val composer: String?,
    val tags: List<String> = emptyList(), // Serialize with a type converter
    val filePath: String,
    val pageCount: Int,
    val lastOpened: LocalDateTime?,
    val addedDate: LocalDateTime = LocalDateTime.now()
)