package com.luteapp.timerpx.util

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import android.util.Log
import com.luteapp.timerpx.database.TimerDef

fun hasAlarmApps(context: Context): Boolean {
    val list = context.packageManager.queryIntentActivities(
        Intent(AlarmClock.ACTION_SET_TIMER), 0
    )
    for (app in list) {
        Log.d("AlarmApps", app.activityInfo.packageName)
    }
    return list.isNotEmpty()
}

fun formatTime(timerDef: TimerDef): String {
    return String.format("%02d:%02d",
        timerDef.duration / 3600,
        (timerDef.duration % 3600) / 60
    )
}

fun formatTime(duration: Long): String {
    return String.format("%02d:%02d",
        duration / 3600,
        (duration % 3600) / 60
    )
}