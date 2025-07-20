package com.example.sheetbox.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sheetbox.core.data.converters.Converters
import com.example.sheetbox.core.data.dao.ScoreDao
import com.example.sheetbox.core.domain.Score

@Database(entities = [Score::class], version = 1)
@TypeConverters(Converters::class)
@OptIn(kotlin.uuid.ExperimentalUuidApi::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scoreDao(): ScoreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sheetbox_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}