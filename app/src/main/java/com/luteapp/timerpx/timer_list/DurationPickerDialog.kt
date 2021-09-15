package com.luteapp.timerpx.timer_list

import android.app.Activity
import android.widget.TimePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.luteapp.timerpx.R

class DurationPickerDialog(
    private val activity: Activity,
    private val listener: OnDurationSelectedListener
) {

    private var hour: Int = 1
    private var minute: Int = 0

    fun show() {
        val timePicker = TimePicker(activity)
        timePicker.setIs24HourView(true)
        timePicker.hour = hour
        timePicker.minute = minute
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            this.hour = hourOfDay
            this.minute = minute
        }
        MaterialAlertDialogBuilder(activity)
            .setTitle(R.string.title_select_duration)
            .setView(timePicker)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                listener.onDurationSelected(hour, minute)
                dialog.dismiss()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    interface OnDurationSelectedListener {
        fun onDurationSelected(hour: Int, minute: Int)
    }
}