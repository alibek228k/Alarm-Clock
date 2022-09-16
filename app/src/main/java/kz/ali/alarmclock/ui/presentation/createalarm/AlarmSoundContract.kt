package kz.ali.alarmclock.ui.presentation.createalarm

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import kz.ali.alarmclock.domain.model.Alarm
import kz.ali.alarmclock.ui.presentation.alarmsound.AlarmSoundActivity

class AlarmSoundContract : ActivityResultContract<Alarm.Ringtone, Alarm.Ringtone?>() {
    override fun createIntent(context: Context, input: Alarm.Ringtone?): Intent {
        return AlarmSoundActivity.newInstance(context, input)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Alarm.Ringtone? {
        if (resultCode != Activity.RESULT_OK) return null
        if (intent == null) return null
        return intent.getParcelableExtra(AlarmSoundActivity.RINGTONE_EXTRA)
    }
}