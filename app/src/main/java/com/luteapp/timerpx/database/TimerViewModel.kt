package com.luteapp.timerpx.database

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TimerRepository
    val allTimers: LiveData<List<TimerDef>>

    init {
        val timerDao = Database.getDatabase(application.applicationContext).timerDao()
        repository = TimerRepository(timerDao)
        allTimers = repository.allTimers
    }

    fun insert(timerDef: TimerDef) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timerDef)
    }

    fun delete(timerDef: TimerDef) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(timerDef)
    }

    class Factory constructor(private val application: Application) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
                TimerViewModel(this.application) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }

    }
}