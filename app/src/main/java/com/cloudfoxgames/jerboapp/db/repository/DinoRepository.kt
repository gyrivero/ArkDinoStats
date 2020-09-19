package com.cloudfoxgames.jerboapp.db.repository

import androidx.lifecycle.LiveData
import com.cloudfoxgames.jerboapp.db.dao.DinoDao
import com.cloudfoxgames.jerboapp.db.entity.DinoEntity

class DinoRepository(private val dinoDao: DinoDao) {
    val allDinos: LiveData<List<DinoEntity>> = dinoDao.getDinosOrderASC()

    suspend fun insert(dino: DinoEntity) {
        dinoDao.insert(dino)
    }

    suspend fun deleteAll() {
        dinoDao.deleteAll()
    }

    suspend fun deleteById(idDino : Int) {
        dinoDao.deleteById(idDino)
    }

    suspend fun update(dino: DinoEntity) {
        dinoDao.update(dino)
    }
}