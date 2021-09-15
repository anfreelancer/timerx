package com.luteapp.timerpx.database

import androidx.lifecycle.LiveData

class TimerRepository(private val timerDao: TimerDao) {
    val allTimers: LiveData<List<TimerDef>> = timerDao.getAll()

    suspend fun insert(timerDef: TimerDef) {
        timerDao.insert(timerDef)
    }

    suspend fun delete(timerDef: TimerDef) {
        timerDao.delete(timerDef)
    }
}