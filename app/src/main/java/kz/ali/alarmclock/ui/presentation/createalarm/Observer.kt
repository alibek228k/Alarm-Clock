package kz.ali.alarmclock.ui.presentation.createalarm

import kz.ali.alarmclock.domain.model.Alarm

interface Observer {
    fun notify(day: Alarm.Days)
}