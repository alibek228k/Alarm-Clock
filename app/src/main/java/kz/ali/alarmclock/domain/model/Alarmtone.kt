package kz.ali.alarmclock.domain.model

import android.net.Uri
import android.provider.Settings


private val defaultAlarmUri = Settings.System.DEFAULT_ALARM_ALERT_URI.toString()

fun Alarmtone.ringtoneManagerString(): Uri? {
    return when (this) {
        is Alarmtone.Silent -> null
        is Alarmtone.Default -> Uri.parse(defaultAlarmUri)
        is Alarmtone.Sound -> Uri.parse(this.uriString)
    }
}

sealed class Alarmtone {
    object Silent : Alarmtone()
    object Default : Alarmtone()
    data class Sound(val uriString: String) : Alarmtone()

//    val persistedString: String?
//        get() =
//            when (this) {
//                is Silent -> null
//                is Default -> ""
//                is Sound -> uriString
//            }
//    companion object {
//        fun fromString(string: String?): Alarmtone {
//            return when (string) {
//                null -> Silent
//                "" -> Default
//                defaultAlarmUri -> Default
//                else -> Sound(string)
//            }
//        }
//    }
}