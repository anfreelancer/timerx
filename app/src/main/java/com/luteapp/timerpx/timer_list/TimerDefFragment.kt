package com.luteapp.timerpx.timer_list

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.luteapp.timerpx.BillingClientSetup
import com.luteapp.timerpx.R
import com.luteapp.timerpx.alarm.AddAlarmActivity
import com.luteapp.timerpx.database.TimerDef
import com.luteapp.timerpx.database.TimerViewModel
import com.luteapp.timerpx.databinding.FragmentTimerListBinding
import com.luteapp.timerpx.timer_list.DurationPickerDialog.OnDurationSelectedListener
import com.luteapp.timerpx.util.formatTime
import java.util.concurrent.TimeUnit

/**
 * A fragment representing a list of Items.
 */
class TimerDefFragment : Fragment(), TimerDefRecyclerViewAdapter.OnTimerInteractionListener,
    OnDurationSelectedListener {

    private val items = ArrayList<TimerDef>()
    private lateinit var viewModel: TimerViewModel
    private var _binding: FragmentTimerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let { activity ->
            viewModel =
                ViewModelProvider(activity, TimerViewModel.Factory(activity.application)).get()
            viewModel.allTimers.observe(this, { timers ->
                timers?.let { it_timers ->
                    Log.d("Observer", "New list received with %d items".format(timers.size))
                    items.clear()
                    if (it_timers.isEmpty()) {
                        binding.textEmpty.visibility = View.VISIBLE
                        binding.list.visibility = View.GONE
                    } else {
                        binding.textEmpty.visibility = View.GONE
                        binding.list.visibility = View.VISIBLE
                        updateShortcuts(activity.applicationContext, it_timers)
                    }
                    items.addAll(it_timers)
                    binding.list.adapter?.notifyDataSetChanged()
                }
            })
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerListBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = TimerDefRecyclerViewAdapter(items, this)

        binding.btnAddTimer.setOnClickListener {
            if(BillingClientSetup.isUpgraded(activity)){
                activity?.let { DurationPickerDialog(it, this).show() }

            }

        }
        return view
    }

    override fun onTimerClicked(timerDef: TimerDef) {
        val intent = Intent(activity, AddAlarmActivity::class.java).apply {
            putExtra(AddAlarmActivity.EXTRA_DELAY, timerDef.duration)
        }
        startActivity(intent)
    }

    override fun onTimerRemovedClicked(timerDef: TimerDef) {
        viewModel.delete(timerDef)
    }

    private fun updateShortcuts(context: Context, list: List<TimerDef>) {
        val shortcutManager = context.getSystemService<ShortcutManager>(ShortcutManager::class.java)
        val shortcuts = ArrayList<ShortcutInfo>()
        for (item in list) {
            shortcuts.add(
                ShortcutInfo.Builder(context, item.duration.toString())
                    .setShortLabel(formatTime(item))
                    .setIcon(Icon.createWithResource(context, R.drawable.ic_shortcut_access_time))
                    .setIntent(
                        Intent(context, AddAlarmActivity::class.java)
                            .setAction(
                                "%s%d".format(
                                    getString(R.string.app_name),
                                    item.duration
                                )
                            )
                            .putExtra(AddAlarmActivity.EXTRA_DELAY, item.duration)
                            .setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                    )
                    .build()
            )
            if (shortcuts.size > 4) {
                break
            }
        }
        shortcutManager!!.dynamicShortcuts = shortcuts
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            TimerDefFragment()
    }

    override fun onDurationSelected(hour: Int, minute: Int) {
        val duration = TimeUnit.HOURS.toSeconds(hour.toLong()) +
                TimeUnit.MINUTES.toSeconds(minute.toLong())
        viewModel.insert(TimerDef(duration))
    }
}