package com.example.arkdinostats.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.arkdinostats.db.entity.DinoEntity

@Dao
interface DinoDao {
    @Insert
    suspend fun insert(dino: DinoEntity)

    @Query("DELETE FROM dinos WHERE id = :idDino")
    suspend fun deleteById(idDino: Int)

    @Query("SELECT * FROM dinos ORDER BY name ASC")
    fun getDinosOrderASC(): LiveData<List<DinoEntity>>

    @Query("DELETE FROM dinos")
    suspend fun deleteAll()

    @Update
    suspend fun update(dino: DinoEntity)
}