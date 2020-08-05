package com.example.arkdinostats.db.repository

import androidx.lifecycle.LiveData
import com.example.arkdinostats.db.dao.DinoDao
import com.example.arkdinostats.db.entity.DinoEntity

class DinoRepository(private val dinoDao: DinoDao) {
    val allDinos: LiveData<List<DinoEntity>> = dinoDao.getDinosOrderASC()

    fun getDinosByNameOrderASC(name: String): LiveData<List<DinoEntity>> {
        return dinoDao.getDinosByNameOrderASC(name)
    }

    suspend fun insert(dino: DinoEntity) {
        dinoDao.insert(dino)
    }

    suspend fun deleteAll() {
        dinoDao.deleteAll()
    }

    suspend fun deleteById(idDino : Int) {
        dinoDao.deleteById(idDino)
    }
}