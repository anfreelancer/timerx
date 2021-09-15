package com.luteapp.timerpx.alarm

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.luteapp.timerpx.MainActivity
import com.luteapp.timerpx.R
import com.luteapp.timerpx.util.formatTime
import com.luteapp.timerpx.util.hasAlarmApps
import java.util.*

class AddAlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("AddAlarm", "onCreate")

        if (hasAlarmApps(this)) {
            Log.d("AddAlarm", "we have alarm apps")
            setAlarm()
        } else {
            Log.d("AddAlarm", "no alarm apps")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setAlarm() {
        val delay: Long = intent.getLongExtra(EXTRA_DELAY, 300)
        val delayHour = delay / 3600
        val delayMinute = (delay % 3600) / 60
        Log.d("AddAlarm", "delay %s".format(formatTime(delay)))

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, delayHour.toInt())
        calendar.add(Calendar.MINUTE, delayMinute.toInt())
        startActivity(
            Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY))
                .putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE))
                .putExtra(AlarmClock.EXTRA_MESSAGE, getString(R.string.app_name))
                .putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        )
        finish()
    }


    companion object {
        const val EXTRA_DELAY = "delay"
    }
}