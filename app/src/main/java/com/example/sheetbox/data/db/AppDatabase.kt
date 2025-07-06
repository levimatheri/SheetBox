package com.example.sheetbox.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sheetbox.data.converters.Converters
import com.example.sheetbox.data.dao.ScoreDao
import com.example.sheetbox.data.entities.Score

@Database(entities = [Score::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao
}