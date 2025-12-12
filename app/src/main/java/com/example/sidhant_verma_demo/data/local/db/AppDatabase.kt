package com.example.sidhant_verma_demo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sidhant_verma_demo.data.local.entity.HoldingsEntity

@Database(entities = [HoldingsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}
