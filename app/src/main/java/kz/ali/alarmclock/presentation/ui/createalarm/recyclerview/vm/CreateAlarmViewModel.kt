package kz.ali.alarmclock.presentation.ui.createalarm.recyclerview.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.ali.alarmclock.domain.model.Alarm

class CreateAlarmViewModel : ViewModel() {

    private val newAlarmCreated: MutableLiveData<Alarm?> by lazy { MutableLiveData<Alarm?>(null) }
    fun getAlarm(): LiveData<Alarm?> = newAlarmCreated

    private val days: MutableLiveData<Alarm.Days?> by lazy { MutableLiveData<Alarm.Days?>(null) }
    fun getDays(): LiveData<Alarm.Days?> = days

    fun createAlarm(alarm: Alarm) {

    }

    fun notify(day: Alarm.Days?) {
        if (day != null) {
            days.postValue(day)
        }
    }

}