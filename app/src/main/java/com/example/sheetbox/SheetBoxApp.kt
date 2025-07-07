package com.example.sheetbox

import android.app.Application
import androidx.room.Room
import com.example.sheetbox.core.data.db.AppDatabase

class SheetBoxApp : Application() {
    // Singleton instance of the database
    private lateinit var database: AppDatabase

    val db: AppDatabase
        get() = database

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "sheetbox_db"
        ).build()
    }
}