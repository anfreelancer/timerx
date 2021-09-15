package com.luteapp.timerpx.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TimerDao {
    @Query("SELECT * FROM TimerDef")
    fun getAll(): LiveData<List<TimerDef>>

    @Query("SELECT * from TimerDef WHERE duration = :duration")
    fun get(duration: Long): LiveData<TimerDef>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg timer: TimerDef)

    @Delete
    fun delete(vararg timer: TimerDef)
}