package kz.ali.alarmclock.ui.presentation.createalarm

import kz.ali.alarmclock.domain.model.Alarm

interface Observer {
    fun notify(day: Alarm.Days?, shouldAdd: Boolean)
}

class ObserverImp(
    private val observer: Observer,
) {
    fun useNotification(day: Alarm.Days? = null, shouldAdd: Boolean = false) {
        observer.notify(day, shouldAdd)
    }
}