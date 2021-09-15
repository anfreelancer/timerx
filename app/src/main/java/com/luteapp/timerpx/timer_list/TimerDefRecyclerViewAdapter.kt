package com.luteapp.timerpx.timer_list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luteapp.timerpx.R
import com.luteapp.timerpx.database.TimerDef
import com.luteapp.timerpx.util.formatTime

/**
 * [RecyclerView.Adapter] that can display a [TimerDef].
 */
class TimerDefRecyclerViewAdapter(
    private val values: List<TimerDef>,
    private val onTimerInteractionListener: OnTimerInteractionListener
) : RecyclerView.Adapter<TimerDefRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_timer, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Log.d("Adapter", "duration:  %s".format(item.duration))
        holder.itemView.tag = item
        holder.bind(item, onTimerInteractionListener)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val contentView: TextView = view.findViewById(R.id.content)
        private val btnRemove: ImageButton = view.findViewById(R.id.btn_remove)

        fun bind(timerDef: TimerDef, onTimerInteractionListener: OnTimerInteractionListener) {
            contentView.text = formatTime(timerDef)
            itemView.setOnClickListener { onTimerInteractionListener.onTimerClicked(timerDef) }
            btnRemove.setOnClickListener { onTimerInteractionListener.onTimerRemovedClicked(timerDef) }
        }

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    interface OnTimerInteractionListener {
        fun onTimerClicked(timerDef: TimerDef)
        fun onTimerRemovedClicked(timerDef: TimerDef)
    }
}