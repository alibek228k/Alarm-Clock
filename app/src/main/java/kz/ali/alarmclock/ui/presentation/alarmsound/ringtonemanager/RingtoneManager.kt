package kz.ali.alarmclock.ui.presentation.alarmsound.ringtonemanager

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import kz.ali.alarmclock.domain.model.Alarm

class RingtoneManager(private val context: Context) {

    private var ringtoneManager: Ringtone? = null

    fun getTitleOfRingtone(stringUri: String? = null): String {
        val uri = Uri.parse(stringUri)
        return if (uri != null) {
            val ringtone = RingtoneManager.getRingtone(context, uri)
            ringtone.getTitle(context)
        } else {
            val defaultUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val ringtoneManager = RingtoneManager.getRingtone(context, defaultUri)
            ringtoneManager.getTitle(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun playAvailableRingtone(ringtone: Alarm.Ringtone?) {
        ringtoneManager = RingtoneManager.getRingtone(context, Uri.parse(ringtone?.ringtoneUri))
        ringtoneManager?.play()
    }

    fun stopAvailableRingtone() {
        ringtoneManager?.stop()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun getVolume(): Float? {
        return ringtoneManager?.volume
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun setVolume(volume: Float) {
        ringtoneManager?.volume = volume
    }

    fun getDefaultRingtoneUri(): String =
        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE).toString()
}

fun Int.volumeToFloat(): Float {
    return 1f / 15f * toFloat()
}

fun Float.volumeToInt(): Int {
    return (this / (1f / 15f)).toInt()
}