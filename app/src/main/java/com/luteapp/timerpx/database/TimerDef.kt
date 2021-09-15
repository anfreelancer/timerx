package com.luteapp.timerpx.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TimerDef(
    @PrimaryKey val duration: Long
)