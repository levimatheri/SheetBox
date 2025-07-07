package com.example.sheetbox.core.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sheetbox.core.domain.Score
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {
    @Query("SELECT * FROM scores ORDER BY addedDate DESC")
    fun getAllScores(): Flow<List<Score>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertScore(score: Score)

    @Delete
    suspend fun deleteScore(score: Score)
}