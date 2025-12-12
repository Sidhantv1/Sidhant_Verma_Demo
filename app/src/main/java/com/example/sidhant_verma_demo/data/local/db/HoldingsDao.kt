package com.example.sidhant_verma_demo.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sidhant_verma_demo.data.local.entity.HoldingsEntity

@Dao
interface HoldingsDao {

    @Query("SELECT * FROM holdings")
    suspend fun getAll(): List<HoldingsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<HoldingsEntity>)
}

