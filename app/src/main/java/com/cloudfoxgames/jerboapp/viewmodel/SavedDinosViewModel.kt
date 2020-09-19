package com.cloudfoxgames.jerboapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cloudfoxgames.jerboapp.db.DinoRoomDatabase
import com.cloudfoxgames.jerboapp.db.entity.DinoEntity
import com.cloudfoxgames.jerboapp.db.repository.DinoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedDinosViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DinoRepository
    val allDinos: LiveData<List<DinoEntity>>

    init {
        val dinoDao = DinoRoomDatabase.getDatabase(application).dinoDao()
        repository = DinoRepository(dinoDao)
        allDinos = repository.allDinos
    }

    fun insert(dino: DinoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dino)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    fun deleteById(dinoId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteById(dinoId)
    }

    fun update(dino: DinoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(dino)
    }
}