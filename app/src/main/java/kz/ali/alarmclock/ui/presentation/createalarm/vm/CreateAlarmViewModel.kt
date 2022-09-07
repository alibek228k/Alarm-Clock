package kz.ali.alarmclock.ui.presentation.createalarm.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.ali.alarmclock.domain.model.Alarm

class CreateAlarmViewModel : ViewModel() {

    private val newAlarmCreated: MutableLiveData<Alarm?> by lazy { MutableLiveData<Alarm?>(null) }
    fun getAlarm(): LiveData<Alarm?> = newAlarmCreated

    fun createAlarm(alarm: Alarm) {

    }

}