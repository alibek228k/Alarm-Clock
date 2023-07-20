package kz.ali.alarmclock.presentation.ui.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.ali.alarmclock.data.local.DatabaseHelper
import kz.ali.alarmclock.domain.model.Alarm
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val databaseHelper: DatabaseHelper,
    application: Application
) : AndroidViewModel(application) {
    private var realListOfAlarms = listOf<Alarm>()

    fun saveAlarms(alarms: List<Alarm>? = realListOfAlarms) {
        if (alarms != null) {
            databaseHelper.saveAlarms(alarms)
            realListOfAlarms = alarms
        }
    }

    fun getAlarms(): List<Alarm> {
        return databaseHelper.getAlarmsList()
    }
}