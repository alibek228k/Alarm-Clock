package kz.ali.alarmclock.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.ali.alarmclock.domain.model.Alarm
import java.lang.reflect.Type

class DatabaseHelper constructor(
    private val context: Context,
) {
    private var alarmsData: SharedPreferences? = null
    private val gson: Gson = Gson()

    fun saveAlarms(alarmsList: List<Alarm>) {
        alarmsData = context.getSharedPreferences("alarmsData", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = alarmsData?.edit()
        editor?.putString("alarms", gson.toJson(alarmsList))
        editor?.apply()
    }

    fun getAlarmsList(): List<Alarm> {
        alarmsData = context.getSharedPreferences("alarmsData", Context.MODE_PRIVATE)
        return try {
            val alarmsString = alarmsData?.getString("alarms", null)
            val type: Type = object : TypeToken<List<Alarm>>() {}.type
            gson.fromJson(alarmsString, type)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            emptyList<Alarm>()
        }
    }

}