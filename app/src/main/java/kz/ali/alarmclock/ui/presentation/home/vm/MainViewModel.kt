package kz.ali.alarmclock.ui.presentation.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kz.ali.alarmclock.data.local.DatabaseHelper
import kz.ali.alarmclock.domain.model.Alarm

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var databaseHelper: DatabaseHelper = DatabaseHelper(application.applicationContext)
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