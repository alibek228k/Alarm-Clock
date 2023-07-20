package kz.ali.alarmclock.presentation.ui.home

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textview.MaterialTextView
import kz.ali.alarmclock.R
import kz.ali.alarmclock.domain.model.Alarm

class AlarmViewHolder(
    itemView: View,
    private val callback: AlarmsAdapter.Callback,
) : RecyclerView.ViewHolder(itemView) {

    private val activeDays = HashMap<String, Boolean>()

    private val title: MaterialTextView =
        itemView.findViewById(R.id.alarmName)
    private val alarmTime: MaterialTextView =
        itemView.findViewById(R.id.alarmTime)
    private val buttonSwitch: SwitchMaterial =
        itemView.findViewById(R.id.buttonSwitch)
    private val alarmDaysMonday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysMonday)
    private val alarmDaysTuesday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysTuesday)
    private val alarmDaysWednesday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysWednesday)
    private val alarmDaysThursday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysThursday)
    private val alarmDaysFriday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysFriday)
    private val alarmDaysSaturday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysSaturday)
    private val alarmDaysSunday: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysSunday)
    private val alarmDaysEveryDay: MaterialTextView =
        itemView.findViewById(R.id.alarmDaysEveryDay)

    fun bind(alarm: Alarm) {
        alarm.apply {
            title.text = name
            alarmTime.text = time
            buttonSwitch.isChecked = isActive
            setAlarmDays(days)
            setupSwitchButton(isActive)
        }
        itemView.setOnClickListener {
            callback.onItemClicked(alarm)
        }

        buttonSwitch.setOnCheckedChangeListener { _, p1 ->
            alarm.isActive = p1
            setupSwitchButton(p1)
            callback.onCheckedStateChanged()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setAlarmDays(days: List<Alarm.Days>) {
        var counter = 0
        for (i in days.indices) {
            if (days[i] == Alarm.Days.MONDAY) {
                counter++
                alarmDaysMonday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["MONDAY"] = true
            } else if (days[i] == Alarm.Days.TUESDAY) {
                counter++
                alarmDaysTuesday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["TUESDAY"] = true
            } else if (days[i] == Alarm.Days.WEDNESDAY) {
                counter++
                alarmDaysWednesday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["WEDNESDAY"] = true
            } else if (days[i] == Alarm.Days.THURSDAY) {
                counter++
                alarmDaysThursday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["THURSDAY"] = true
            } else if (days[i] == Alarm.Days.FRIDAY) {
                counter++
                alarmDaysFriday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["FRIDAY"] = true
            } else if (days[i] == Alarm.Days.SATURDAY) {
                counter++
                alarmDaysSaturday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["SATURDAY"] = true
            } else if (days[i] == Alarm.Days.SUNDAY) {
                counter++
                alarmDaysSunday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
                activeDays["SUNDAY"] = true
            }
        }
        if (counter == 7) {
            alarmDaysMonday.visibility = View.GONE
            alarmDaysTuesday.visibility = View.GONE
            alarmDaysWednesday.visibility = View.GONE
            alarmDaysThursday.visibility = View.GONE
            alarmDaysFriday.visibility = View.GONE
            alarmDaysSaturday.visibility = View.GONE
            alarmDaysSunday.visibility = View.GONE

            alarmDaysEveryDay.visibility = View.VISIBLE
        }
    }

    private fun setupSwitchButton(isChecked: Boolean) {
        if (isChecked) {
            alarmTime.setTextColor(itemView.resources.getColor(R.color.fontColor, null))
            title.setTextColor(itemView.resources.getColor(R.color.fontColor, null))
            if (activeDays["MONDAY"] == true) {
                alarmDaysMonday.setTextColor(itemView.resources.getColor(R.color.purple_200, null))
            }
            if (activeDays["TUESDAY"] == true) {
                alarmDaysTuesday.setTextColor(itemView.resources.getColor(R.color.purple_200, null))
            }
            if (activeDays["WEDNESDAY"] == true) {
                alarmDaysWednesday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
            }
            if (activeDays["THURSDAY"] == true) {
                alarmDaysThursday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
            }
            if (activeDays["FRIDAY"] == true) {
                alarmDaysFriday.setTextColor(itemView.resources.getColor(R.color.purple_200, null))
            }
            if (activeDays["SATURDAY"] == true) {
                alarmDaysSaturday.setTextColor(
                    itemView.resources.getColor(
                        R.color.purple_200,
                        null
                    )
                )
            }
            if (activeDays["SUNDAY"] == true) {
                alarmDaysSunday.setTextColor(itemView.resources.getColor(R.color.purple_200, null))
            }
            alarmDaysEveryDay.setTextColor(itemView.resources.getColor(R.color.purple_200, null))
        } else {
            alarmTime.setTextColor(itemView.resources.getColor(R.color.disabled_state_color, null))
            title.setTextColor(itemView.resources.getColor(R.color.disabled_state_color, null))
            alarmDaysEveryDay.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysMonday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysTuesday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysWednesday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysThursday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysFriday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysSaturday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
            alarmDaysSunday.setTextColor(
                itemView.resources.getColor(
                    R.color.disabled_state_color,
                    null
                )
            )
        }
    }
}
