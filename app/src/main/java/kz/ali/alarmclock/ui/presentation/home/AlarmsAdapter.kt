package kz.ali.alarmclock.ui.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm

class AlarmsAdapter(
    private val context: Context,
    private val callback: Callback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var alarms = mutableListOf<Alarm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AlarmViewHolder(
            LayoutInflater.from(context).inflate(R.layout.alarm_item, parent, false),
            callback
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlarmViewHolder) {
            holder.bind(alarms[position])
        }
    }

    override fun getItemCount(): Int {
        return alarms.size
    }

    fun getAlarms(): List<Alarm> = alarms

    fun setAlarms(newAlarms: List<Alarm>, notify: Boolean = true) {
        if (newAlarms.isEmpty()) return
        if (newAlarms.isNotEmpty()) {
            val size = alarms.size
            alarms.clear()
            if (notify) {
                notifyItemRangeRemoved(0, size - 1)
            }
        }
        alarms.addAll(newAlarms)
        if (notify) {
            notifyItemRangeInserted(0, alarms.size - 1)
        }
    }

    fun addAlarm(alarm: Alarm, notify: Boolean = true) {
        alarms.add(0, alarm)
        if (alarms.contains(alarm)) {
            notifyItemInserted(0)
        }
    }

    fun clear() {
        val size = itemCount
        alarms.clear()
        if (alarms.isEmpty()) {
            notifyItemRangeRemoved(0, size)
        }
    }

    interface Callback {
        fun onItemClicked()
        fun onCheckedStateChanged()
    }
}
